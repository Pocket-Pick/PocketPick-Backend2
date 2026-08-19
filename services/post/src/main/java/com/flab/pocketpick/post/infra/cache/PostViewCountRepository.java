package com.flab.pocketpick.post.infra.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostViewCountRepository {

    private static final String KEY_PREFIX = "post:views:";
    private static final Duration TTL = Duration.ofDays(7);

    private static final RedisScript<Long> INCREMENT_AND_EXPIRE_SCRIPT = RedisScript.of(
            """
            local current = redis.call('INCR', KEYS[1])
            redis.call('EXPIRE', KEYS[1], ARGV[1])
            return current
            """,
            Long.class
    );

    private final RedisTemplate<String, Long> longRedisTemplate;

    public Long increment(Long postId) {
        return longRedisTemplate.execute(
                INCREMENT_AND_EXPIRE_SCRIPT,
                List.of(key(postId)),
                String.valueOf(TTL.getSeconds())
        );
    }

    public List<PostViewCount> getAll() {
        ScanOptions options = ScanOptions.scanOptions()
                .match(KEY_PREFIX + "*")
                .count(100)
                .build();

        List<String> keyList;
        try (Cursor<String> cursor = longRedisTemplate.scan(options)) {
            keyList = cursor.stream().toList();
        }

        if (keyList.isEmpty()) {
            return List.of();
        }

        List<Long> values = longRedisTemplate.opsForValue().multiGet(keyList);

        List<PostViewCount> result = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            Long value = values.get(i);
            if (value != null) {
                Long postId = Long.parseLong(keyList.get(i).substring(KEY_PREFIX.length()));
                result.add(new PostViewCount(postId, value));
            }
        }
        return result;
    }

    private String key(Long postId) {
        return KEY_PREFIX + postId;
    }
}
