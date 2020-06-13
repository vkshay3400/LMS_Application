package com.bridgelabz.lmsapi.util;

public interface Redis{

    void save(String RedisKey, String email, String authenticationToken);

    Object getRedisToken(String redisKey, String userName);

}
