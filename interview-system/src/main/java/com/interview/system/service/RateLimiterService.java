package com.interview.system.service;

import com.interview.common.utils.redis.RedisUtils;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final RedissonClient redissonClient = RedisUtils.getClient();

    public boolean allow(String userKey) {
        String key = "tts:limiter:" + userKey;
        RRateLimiter limiter = redissonClient.getRateLimiter(key);
        if (!limiter.isExists()) {
            // 每两秒加一个令牌桶，最多两秒
            limiter.trySetRate(RateType.OVERALL, 2, 4, RateIntervalUnit.SECONDS);
        }
        return limiter.tryAcquire(1);
    }
}
