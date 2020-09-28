package com.zhqn.chat.controller;

import com.zhqn.chat.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 周全
 * @date 2020/9/28 15:38
 * @description <p>
 */
@Controller
@RequestMapping("/public")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginPost(@Validated LoginDto loginDto, HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "/public/chat";
        }
//        if ()
        session.setAttribute("username", loginDto.getUsername());
        return null;
    }
}
