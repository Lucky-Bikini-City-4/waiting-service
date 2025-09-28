package com.dayaeyak.waiting.domain.worker;


import com.dayaeyak.waiting.domain.enums.Transition;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.repository.cache.redis.WaitingCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.dayaeyak.waiting.domain.enums.WaitingStatus.*;

@Component
@RequiredArgsConstructor
public class TimeoutConsumer {

    private final StringRedisTemplate redis;
    private final WaitingCacheRepository cache;

    // 지연 큐 키 (deadlineAt 점수로 정렬된 ZSET)
    private static final String DELAY_ZSET = "waiting:deadline:zset";
    // 실패 시 임시 보관하는 리스트
    private static final String DLQ_LIST   = "waiting:dlq:list";
    // 락 소유자 표시에 쓰는 랜덤 아이디
    private final String workerId = UUID.randomUUID().toString();

    // 튜닝 포인트: 한 번에 처리할 최대 건수
    @Value("${waiting.consumer.batch-size:100}")
    private int batchSize;

    // 1초마다 기한 지난 작업 처리: 이 메서드를 실행. 지연 큐를 폴링해서 처리
    @Scheduled(fixedDelayString = "${waiting.consumer.fixed-delay-ms:1000}")
    public void pollAndProcess() {
        long now = System.currentTimeMillis();

        Set<String> jobs = redis.opsForZSet()
                .rangeByScore(DELAY_ZSET, 0, now, 0, batchSize);
        if (jobs == null || jobs.isEmpty()) return;

        for (String job : jobs) {
            // job 형식: "<waitingId>#<transition>"
            String[] parts = job.split("#", 2);
            if (parts.length != 2) {
                // 포맷 이상 → DLQ
                redis.opsForList().leftPush(DLQ_LIST, job);
                continue;
            }
            long waitingId = Long.parseLong(parts[0]);
            String transitionName = parts[1];

            String lockKey = "waiting:" + waitingId + ":" + transitionName + ":lock";
            Boolean locked = redis.opsForValue().setIfAbsent(lockKey, workerId, Duration.ofSeconds(30));
            if (Boolean.FALSE.equals(locked)) continue;

            try {
                Long removed = redis.opsForZSet().remove(DELAY_ZSET, job);
                if (removed == null || removed == 0) continue; // 다른 워커가 선처리

                // restaurantId 조회 → 상세 해시 읽기
                Long restaurantId = cache.getRestaurantByWaitingId(waitingId);
                System.out.println("restaurantId : "+ restaurantId);
                if (restaurantId == null) continue;

                var detail = cache.getWaitingDetail(restaurantId, waitingId);
                if (detail == null || detail.isEmpty()) continue;

                String cur = detail.get("status");
                Transition tr = com.dayaeyak.waiting.domain.enums.Transition.valueOf(transitionName);

                switch (tr) {
                    case NO_ANSWER:
                        // 아직 FIRST_CALLED, NO_ANSWER으로 전이
                        if ("FIRST_CALLED".equals(cur)) {
                            Map<String, Object> wo = new HashMap<String, Object>();
                            wo.put("status", WaitingStatus.NO_ANSWER);
                            wo.put("deadlineAt", 0L);
                            cache.hsetFields(restaurantId, waitingId, wo);
                            cache.removeFromByStatus(restaurantId, waitingId, "FIRST_CALLED");
                        }

                        if ("COMMING".equals(cur)) {
                            Map<String, Object> wo = new HashMap<String, Object>();
                            wo.put("status", WaitingStatus.NO_ANSWER);
                            wo.put("deadlineAt", 0L);
                            cache.hsetFields(restaurantId, waitingId, wo);
                            cache.removeFromByStatus(restaurantId, waitingId, "COMMING");
                        }

                        break;

                    case NO_ANSWER2:
                        if ("FINAL_CALLED".equals(cur)) {
                            Map<String, Object> wo = new HashMap<String, Object>();
                            wo.put("status", WaitingStatus.NO_ANSWER2);
                            wo.put("deadlineAt", 0L);
                            cache.hsetFields(restaurantId, waitingId, wo);
                            cache.removeFromByStatus(restaurantId, waitingId, "FINAL_CALLED");
                            cache.unmapWaiting(waitingId);
                        }
                        break;
                }
            } catch (Exception e) {
                redis.opsForList().leftPush(DLQ_LIST, job);
            } finally {
                redis.delete(lockKey);
            }
        }
    }
}
