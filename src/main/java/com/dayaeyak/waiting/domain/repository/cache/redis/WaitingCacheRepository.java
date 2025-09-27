package com.dayaeyak.waiting.domain.repository.cache.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 매장 단위 키스페이스 + ZSET/HASH 기반 대기열 캐시 리포지토리
 */
@Repository
@RequiredArgsConstructor
public class WaitingCacheRepository {

    private final StringRedisTemplate redis;
    private final DefaultRedisScript<Long> waitingStateScript; // Lua (ACTIVATE/DEACTIVATE)

    // ---------- key builders (클러스터 원자성 위해 해시태그 {restaurantId:ID}) ---------- 같은 것 끼리 모아 놓기 위함
    private String kActive(long restaurantId) { return "{restaurantId:" + restaurantId + "}:active"; }
    private String kWaiting(long restaurantId, long waitingId) { return "{restaurantId:" + restaurantId + "}:waitingId:" + waitingId; }
    private String kSummary(long restaurantId) { return "{restaurantId:" + restaurantId + "}:summary"; }

    // ---------- 초기 셋업 / 상세 필드 ----------
    /** 등록 직후 1회: 상세 HASH 초기화(restaurantId/deadlineAt/etaSec 등) */
    public void initWaitingDetail(long restaurantId, long waitingId, long seq, String status,long deadlineAtEpochMs) {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("restaurantId", String.valueOf(restaurantId));
        fields.put("seq", String.valueOf(seq));
        fields.put("status", status);
        fields.put("deadlineAt", String.valueOf(deadlineAtEpochMs));
        redis.opsForHash().putAll(kWaiting(restaurantId, waitingId), fields);
    }

    /** 상세 일부 필드만 업데이트 (예: deadlineAt 갱신) */
    public void hsetFields(long restaurantId, long waitingId, Map<String, Object> fields) {
        if (fields == null || fields.isEmpty()) return;
        Map<String, String> str = fields.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
        redis.opsForHash().putAll(kWaiting(restaurantId, waitingId), str);
    }

    /** 상세 조회 */
    public Map<String, String> getWaitingDetail(long restaurantId, long waitingId) {
        return redis.<String, String>opsForHash().entries(kWaiting(restaurantId, waitingId));
    }

    // ---------- 상태 전이 (원자적: Lua 실행) ----------
    /** 활성화: 줄에 넣기(ZADD) + 상태/HASH 세팅, summary 무효화 */
    public void activate(long restaurantId, long waitingId, long seq, String status) {
        redis.execute(
                waitingStateScript,
                List.of(kActive(restaurantId), kWaiting(restaurantId, waitingId)),
                "ACTIVATE", String.valueOf(seq), String.valueOf(waitingId), status, "0"
        );
        invalidateSummary(restaurantId);
    }

    /** 비활성화: 줄에서 빼기(ZREM) + 상태/HASH/만료 설정, summary 무효화 */
    public void deactivate(long restaurantId, long waitingId, String status, Duration keep) {
        long expireAt = System.currentTimeMillis() + keep.toMillis();
        redis.execute(
                waitingStateScript,
                List.of(kActive(restaurantId), kWaiting(restaurantId, waitingId)),
                "DEACTIVATE", "0", String.valueOf(waitingId), status, String.valueOf(expireAt)
        );
        invalidateSummary(restaurantId);
    }

    /** 즉시 비활성화: 줄에서 빼기(ZREM) + 상태/HASH/만료 설정, summary 무효화 */
    public void deactivateImmediate(long restaurantId, long waitingId) {

        System.out.println("hi "+ restaurantId + " "+ waitingId);
        String k1 = kActive(restaurantId);              // 절대 null 금지
        String k2 = kWaiting(restaurantId, waitingId);  // 절대 null 금지

        String cmd      = "DEACTIVATE";
        String seq      = "0";
        String member   = String.valueOf(waitingId);    // null 금지
        String status   = "DELETED";                    // null 금지 (빈문자라도 String)
        String expireAt = "0";                          // 즉시 삭제

        System.out.println(k1+" "+ k2+" " +member+" " +cmd+" " +status+" " +expireAt+" ");


        redis.execute(
                waitingStateScript,                 // new DefaultRedisScript<>(lua, Long.class)
                List.of(k1, k2),                    // KEYS -> String 리스트
                cmd,
                seq,
                member,
                status,
                expireAt       // ARGV -> 전부 String
        );
        invalidateSummary(restaurantId);
    }

    // ---------- 순번/현황 ----------
    /** 현재 순번: 활성 ZSET 안의 랭크 + 1 (없으면 null) */
    public Long currentOrder(long restaurantId, long waitingId) {
        Long r = redis.opsForZSet().rank(kActive(restaurantId), String.valueOf(waitingId));
        return (r == null) ? null : r + 1;
    }

    /** 활성 팀 수 */
    public long countActive(long restaurantId) {
        Long c = redis.opsForZSet().zCard(kActive(restaurantId));
        return c == null ? 0 : c;
    }

    /** 상위 N명 waitingId */
    public List<Long> topN(long restaurantId, int n) {
        Set<String> range = redis.opsForZSet().range(kActive(restaurantId), 0, Math.max(0, n - 1));
        if (range == null || range.isEmpty()) return List.of();
        return range.stream().map(Long::valueOf).toList();
    }

    // ---------- 요약 캐시 (TTL 1~3s 권장) ----------
    public Map<Object, Object> getSummary(long restaurantId) {
        return redis.opsForHash().entries(kSummary(restaurantId));
    }

    public void setSummary(long restaurantId, Map<String, ?> summary, Duration ttl) {
        String key = kSummary(restaurantId);
        Map<String, String> str = summary.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
        redis.opsForHash().putAll(key, str);
        redis.expire(key, ttl);
    }
    public void invalidateSummary(long restaurantId) {
        redis.delete(kSummary(restaurantId));
    }

    // ---------- 복구/청소 ----------
    /** 특정 대기건 강제 제거(비상용) */
    public void forceRemoveFromActive(long restaurantId, long waitingId) {
        redis.opsForZSet().remove(kActive(restaurantId), String.valueOf(waitingId));
    }
    /** 상세 키 TTL 직접 설정(비상용) */
    public void setDetailTtlUntil(long restaurantId, long waitingId, long expireAtEpochMs) {
        redis.expireAt(kWaiting(restaurantId, waitingId), new Date(expireAtEpochMs).toInstant());
    }
}