package com.zhqn.chat.controller;

import com.zhqn.chat.cst.SessionConstraints;
import com.zhqn.chat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(SessionConstraints.PUBLIC_PATH + "/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("")
    public void add() {

    }
}
