package com.zhqn.chat;

import com.zhqn.chat.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatApplicationTests {

    @Autowired
    RedisTestService redisTestService;

    @Test
    void contextLoads() {
        redisTestService.testGet();
    }

    @Test
    void testLombok() {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(":xxxdqd");
        loginDto.setUsername("dqwdqw");
        System.out.println(loginDto);
    }

}
