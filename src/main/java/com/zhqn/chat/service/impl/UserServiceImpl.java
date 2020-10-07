package com.zhqn.chat.service.impl;

import com.zhqn.chat.dao.UserDao;
import com.zhqn.chat.ex.BaseServiceException;
import com.zhqn.chat.model.User;
import com.zhqn.chat.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@CacheConfig(cacheNames = {"users"})
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Cacheable(key = "#p0")
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User login(User loginUser) {
        if (StringUtils.isEmpty(loginUser.getUsername()) || StringUtils.isEmpty(loginUser.getPassword())) {
            return null;
        }
        User user = userDao.findByUsername(loginUser.getUsername());
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return null;
        }
        return user;

    }

    private void baseSave(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userDao.save(user);
    }

    @CacheEvict(cacheNames = "users", allEntries = true, beforeInvocation = true)
    @Override
    public void add(User user) {
        User dbUser = findByUsername(user.getUsername());
        BaseServiceException.assertOne(dbUser == null, "用户" + user.getUsername() + "已存在");
        user.setId(null);
        baseSave(user);
    }

    @CacheEvict(cacheNames = "users", allEntries = true, beforeInvocation = true)
    @Override
    public void update(User user) {
        if (user.getId() == null) {
            BaseServiceException.throwOne("id不能为空");
        }
        if (user.getVersion() == null) {
            BaseServiceException.throwOne("version不能为空");
        }
        baseSave(user);
    }
}
