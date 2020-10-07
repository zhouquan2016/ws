package com.zhqn.chat.service;

import com.zhqn.chat.model.User;

public interface UserService {

    User findByUsername(String username);

    /**
     * 用户登录
     * @param loginUser
     * @return 登录成功返回用户实体，未成功返回null
     */
    User login(User loginUser);

    void add(User user);

    void update(User user);
}
