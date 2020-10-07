package com.zhqn.chat.controller;

import com.zhqn.chat.cst.SessionConstraints;
import com.zhqn.chat.dto.LoginDto;
import com.zhqn.chat.mapstruct.LoginStruct;
import com.zhqn.chat.model.User;
import com.zhqn.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 周全
 * @date 2020/9/28 15:38
 * @description <p>
 */
@Controller
@RequestMapping(SessionConstraints.PUBLIC_PATH)
@Slf4j
public class LoginController {

    @Resource
    UserService userService;

    @Resource
    LoginStruct loginStruct;

    @GetMapping(SessionConstraints.LOGIN_PATH)
    public String login() {
        return SessionConstraints.LOGIN_PATH;
    }

    @PostMapping(SessionConstraints.LOGIN_PATH)
    public String loginPost(@Validated LoginDto loginDto, HttpSession session, HttpServletRequest request) {
        User loginUser = null;
        String view = SessionConstraints.LOGIN_PATH;
        try {
            loginUser = userService.login(loginStruct.dto2Domain(loginDto));
            if (loginUser == null) {
                request.setAttribute(SessionConstraints.LOGIN_ERROR, "用户或者密码不正确");
            }else {
                session.setAttribute(SessionConstraints.USERNAME, loginUser.getUsername());
                view = request.getContextPath();
            }
        }catch (Exception e) {
            log.error("user " + loginDto.getUsername() + " fail, reason is :" + e.getMessage(), e);
            request.setAttribute(SessionConstraints.LOGIN_ERROR, "系统异常");
        }
        return view;
    }
}
