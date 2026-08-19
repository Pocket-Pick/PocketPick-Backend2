package com.flab.pocketpick.post.infra.cache;

import org.springframework.data.redis.cache.RedisCacheWriter.TtlFunction;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public enum PostCacheTtlFunction implements TtlFunction {
    INSTANCE;

    private static final long BASE_TTL_HOURS = 24;
    private static final long JITTER_MINUTES = 30;

    @Override
    public Duration getTimeToLive(Object key, Object value) {
        long jitter = ThreadLocalRandom.current().nextLong(-JITTER_MINUTES, JITTER_MINUTES + 1);
        return Duration.ofHours(BASE_TTL_HOURS).plusMinutes(jitter);
    }
}
