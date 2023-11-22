package com.moing.backend.global.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {

    private RedisTemplate redisTemplate;

    @Value("${jwt.refresh-token-period}")
    private long refreshTokenValidityTime;

    public RedisUtil(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String refreshToken, String socialId) {
        //동일한 key 값으로 저장하면 value값 updat됨
        redisTemplate.opsForValue().set(socialId, refreshToken, refreshTokenValidityTime/1000, TimeUnit.SECONDS);
    }

    public void deleteById(String socialId) {
        redisTemplate.delete(String.valueOf(socialId));
    }

    public Optional<String> findById(final String socialId) {
        String refreshToken = (String) redisTemplate.opsForValue().get(socialId);
        return Optional.ofNullable(refreshToken);
    }
}
