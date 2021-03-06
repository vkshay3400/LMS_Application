package com.bridgelabz.lmsapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RedisImpl implements Serializable, Redis {

    private static final long serialVersionUID = 1L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String RedisKey, String email, String authenticationToken) {
        redisTemplate.opsForHash().put(RedisKey, email, authenticationToken);
    }

    @Override
    public Object getRedisToken(String redisKey, String userName) {
        return redisTemplate.opsForHash().get(redisKey, userName);
    }
}
