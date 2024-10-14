package com.nhnacademy.webserver.common.utils.loginUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private String HASH_NAME = "SESSION:";

    public String saveUser(String id) {
        String sessionId = UUID.randomUUID().toString();

        redisTemplate.opsForHash().put(HASH_NAME, sessionId, id);
        return sessionId;
    }

    public String getUserId(String sessionId){
        String id = redisTemplate.opsForHash().get(HASH_NAME, sessionId).toString();
        return id;
    }

    public void removeUser(String sessionId) {
        redisTemplate.opsForHash().delete(HASH_NAME, sessionId);
    }
}
