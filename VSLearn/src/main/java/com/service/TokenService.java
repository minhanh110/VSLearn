package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_PREFIX = "token:";
    private static final long TOKEN_EXPIRATION = 3600; // 1 hour in seconds

    public void storeToken(String username, String token) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    public String getToken(String username) {
        String key = TOKEN_PREFIX + username;
        return redisTemplate.opsForValue().get(key);
    }

    public void removeToken(String username) {
        String key = TOKEN_PREFIX + username;
        redisTemplate.delete(key);
    }

    public Map<String, String> getAllTokens() {
        Map<String, String> tokens = new HashMap<>();
        Set<String> keys = redisTemplate.keys(TOKEN_PREFIX + "*");
        
        if (keys != null) {
            for (String key : keys) {
                String username = key.substring(TOKEN_PREFIX.length());
                String token = redisTemplate.opsForValue().get(key);
                if (token != null) {
                    tokens.put(username, token);
                }
            }
        }
        
        return tokens;
    }
} 