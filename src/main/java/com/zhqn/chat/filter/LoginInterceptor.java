package com.zhqn.chat.filter;

import com.zhqn.chat.cst.SessionConstraints;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String) request.getSession().getAttribute(SessionConstraints.USERNAME);
        if (request.getRequestURI().equals(SessionConstraints.LOGIN_URL)) {
            if (username != null) {
                response.sendRedirect(request.getContextPath());
                return false;
            }
        }else {
            if (username == null) {
                response.sendRedirect(SessionConstraints.LOGIN_URL);
                return false;
            }
        }
        return true;
    }
}
