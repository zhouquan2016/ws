package com.zhqn.chat.service;

import com.zhqn.chat.ws.dto.ChatMsg;
import com.zhqn.chat.ws.dto.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 周全
 * @date 2020/9/27 15:07
 * @description <p>
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService{

    @Autowired
    ServerInfo serverInfo;

    Map<String, Session> clientSessionMap = new ConcurrentHashMap<String, Session>();

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    String genRedisKey (String username) {
        return username + "_ip_port";
    }

    @Override
    public void addSession(String username, Session session) {
        Session oldSession = clientSessionMap.put(username, session);
        if (oldSession != null && oldSession != session) {
            try {
                oldSession.close();
            } catch (IOException e) {
                log.error("关闭老的session失败，不影响程序", e);
            }
        }
        redisTemplate.opsForValue().set(genRedisKey(username), String.format("%s:%d", serverInfo.getIp(), serverInfo.getPort()));
    }

    @Override
    public Session getSession(String username) {
        return clientSessionMap.get(genRedisKey(username));
    }

    @Override
    public void removeSession(String username) {
        clientSessionMap.remove(genRedisKey(username));
        redisTemplate.delete(genRedisKey(username));
    }

    @Override
    public void replyMsg(Session toSession, ChatMsg chatMsg) {
        if (toSession == null || !toSession.isOpen()) {
            return;
        }
        try {
            toSession.getBasicRemote().sendObject(chatMsg);
        } catch (Exception e) {
            log.error("websocket reply msg error", e);
        }
    }

    @Override
    public void replyRemote(String toUsername, ChatMsg chatMsg) {
        String ipPort = redisTemplate.opsForValue().get(genRedisKey(toUsername));
        RestTemplate restTemplate = new RestTemplate();
        ChatMsg callMsg = restTemplate.postForObject(String.format("http://%s%s/private/receiveMsg"), chatMsg, ChatMsg.class);
        if (callMsg == null || callMsg.getCode() == null) {
            //远程调用异常处理
            callMsg= new ChatMsg(ChatMsg.ReturnCode.Fail);
            callMsg.setMsg("发送消息失败");
        }
        if (!callMsg.isSuccess()) {
            log.error("remote send msg fail", callMsg.getMsg());
            throw new RuntimeException(callMsg.getMsg());
        }
    }

    @Override
    public void sendMsg(ChatMsg chatMsg) {
        //先从本地查询要推送到的session是否存在
        Session toSession = getSession(chatMsg.getToUsername());
        if (toSession != null) {
            //发送的用户在同一台服务器，直接回复
            replyMsg(toSession, chatMsg);
        }else {
            //不存在同一台服务器需要把消息发送到接收消息的用户所在的服务器
            replyRemote(chatMsg.getToUsername(), chatMsg);
        }

    }

    @Override
    public void receiveMsg(ChatMsg chatMsg) {
        //先从本地查询要推送到的session是否存在
        Session session = getSession(chatMsg.getToUsername());
        if (session == null) {
            //异步写入到数据库
            log.info("receiveMsg:{},but username:{} offline, save db!");
        }else {
            replyMsg(session, chatMsg);
        }
    }
}
