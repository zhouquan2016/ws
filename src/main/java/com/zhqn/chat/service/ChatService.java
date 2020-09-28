package com.zhqn.chat.service;

import com.zhqn.chat.ws.dto.ChatMsg;

import javax.websocket.Session;

/**
 * @author EDZ
 */
public interface ChatService {

    /**
     * 将session保存到本地和redis
     * @param username
     * @param session
     */
    void addSession(String username, Session session);

    /**
     * 根据username得到本地的session
     * @param username
     * @return
     */
    Session  getSession(String username);

    /**
     * 根据username移除session
     * @param username
     */
    void removeSession(String username);

    /**
     * 本地直接回复
     * @param toSession
     * @param chatMsg
     */
    void replyMsg(Session toSession, ChatMsg chatMsg);

    /**
     * 发送的用户不在同一台服务器，需要先把消息发送到对应的服务器上，再回复
     * @param toUsername
     * @param chatMsg
     */
    void replyRemote(String toUsername, ChatMsg chatMsg);

    /**
     * 不存在同一台服务器需要把消息发送到接收消息的用户所在的服务器
     * @param chatMsg
     */
    void sendMsg(ChatMsg chatMsg);

    /**
     * 接收远程发送的消息
     * @param chatMsg
     */
    void receiveMsg(ChatMsg chatMsg);
}
