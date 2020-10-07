package com.zhqn.chat.config;

import com.zhqn.chat.cst.SessionConstraints;
import com.zhqn.chat.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .order(Integer.MIN_VALUE)
                .addPathPatterns(SessionConstraints.PUBLIC_PATH + "/**")
                .excludePathPatterns(SessionConstraints.LOGIN_URL);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String indexPath = "/public/index";
        registry.addRedirectViewController("/", indexPath);
        registry.addViewController(indexPath).setViewName("index");
    }

}
