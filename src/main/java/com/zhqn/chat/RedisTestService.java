package com.zhqn.chat;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 周全
 * @date 2020/9/27 10:56
 * @description <p>
 */
@Service
@Slf4j
public class RedisTestService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void testGet() {
        redisTemplate.opsForValue().set("testGet", "xxxxx");
        String getStr = redisTemplate.opsForValue().get("testGet");
        log.info("getstr = " + getStr);
    }
}
