package com.zhqn.chat;

import com.zhqn.chat.model.User;
import com.zhqn.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    public void testFindByUsername() {
        long st = System.currentTimeMillis();
        int times = 10000;
        for (int i = 0; i < times; ++i ) {
            User user = userService.findByUsername("admin");
//            assert user != null;
        }
        st = System.currentTimeMillis() - st;
        System.out.printf("%d次查询，总耗时:%d,平均:%3.2fms\n", times, st, Math.ceil(1.0 * st / times));

    }
    @Test
    public void testAdd() {
        User user = new User();
        user.setPassword("123");
        user.setUsername("admin");
        user.setName("管理员");
        userService.add(user);
    }

    @Test
    public void tesUpdate() {
        User user = userService.findByUsername("admin");
        assert user != null;
//        user.setVersion(0L);
        user.setName("管理员3");
        userService.update(user);
    }
}
