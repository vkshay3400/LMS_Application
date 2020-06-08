package com.bridgelabz.lmsapi.configuration;

import com.bridgelabz.lmsapi.model.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHostName;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(redisHostName);
        connectionFactory.setPort(redisPort);
        connectionFactory.setPassword(redisPassword);
        return connectionFactory;
    }

    @Bean
    RedisTemplate<String, UserDao> redisTemplate() {
        RedisTemplate<String, UserDao> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }
}
