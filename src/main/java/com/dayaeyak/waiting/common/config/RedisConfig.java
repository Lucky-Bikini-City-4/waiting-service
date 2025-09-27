package com.dayaeyak.waiting.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port
    ) {
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(conf);
    }

    // 1) 문자열 위주(키/필드/멤버가 전부 String인 ZSET/HASH)
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf) {
        StringRedisTemplate t = new StringRedisTemplate();
        t.setConnectionFactory(cf);
        // key/hashKey는 문자열로
        t.setKeySerializer(new StringRedisSerializer());
        t.setHashKeySerializer(new StringRedisSerializer());
        // 기본 serializer는 String으로 충분 (ZSET/HASH 멤버/필드가 문자열)
        return t;
    }

    // 2) 상태 전이 원자처리용 Lua
    @Bean
    public org.springframework.data.redis.core.script.DefaultRedisScript<Long> waitingStateScript() {
        String lua = """
        -- KEYS[1]=activeZset, KEYS[2]=waitingHash
        -- ARGV: op, seq, waitingId, status, expireAt(ms)
        local op = ARGV[1]
        if op == 'ACTIVATE' then
          redis.call('ZADD', KEYS[1], ARGV[2], ARGV[3])
          redis.call('HSET', KEYS[2], 'status', ARGV[4], 'seq', ARGV[2])
          return 1
         else
          local member   = tostring(ARGV[3] or "")
          local status   = tostring(ARGV[4] or "")
          local expireAt = tonumber(ARGV[5]) or 0
    
          redis.call('ZREM', KEYS[1], member)
    
          if expireAt == 0 then
            redis.call('DEL', KEYS[2])                  -- 즉시 삭제
          else
            redis.call('HSET', KEYS[2], 'status', status, 'expireAt', expireAt)
            redis.call('PEXPIREAT', KEYS[2], expireAt)  -- 밀리초 만료
          end
          return 1
        end
        """;
        var script = new org.springframework.data.redis.core.script.DefaultRedisScript<Long>();
        script.setScriptText(lua);
        script.setResultType(Long.class);
        return script;
    }
}
