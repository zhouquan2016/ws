package com.zhqn.chat.ws.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 周全
 * @date 2020/9/27 14:44
 * @description <p>
 */
@Data
@Slf4j
@Component
public class ServerInfo {

    private String ip;
    @Value("${server.port}")
    private String port;
    @Value("server.servlet.context-path")
    private String contextPath;

    @PostConstruct
    public void init() throws UnknownHostException {
        ip = InetAddress.getLocalHost().getHostAddress();
        log.info("本机ip:{},port:{}", ip, port);
    }
}
