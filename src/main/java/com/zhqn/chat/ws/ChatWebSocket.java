package com.zhqn.chat.ws;

import com.zhqn.chat.config.GetHttpSessionConfigurator;
import com.zhqn.chat.ex.NotLoginException;
import com.zhqn.chat.ex.SendMsgException;
import com.zhqn.chat.service.ChatService;
import com.zhqn.chat.util.JsonUtils;
import com.zhqn.chat.ws.dto.ChatMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author 周全
 * @date 2020/9/27 13:46
 * @description <p>
 */
@ServerEndpoint(value = "/ws/{username}", configurator = GetHttpSessionConfigurator.class)
@Slf4j
public class ChatWebSocket {

    @Autowired
    ChatService chatService;

    private final String usernameKey = "username";

    private String getUsername(Session session) {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        String username = (String) httpSession.getAttribute(usernameKey);
        if(username == null) {
            throw new NotLoginException();
        }
        return username;
    }
    @OnOpen
    public void onOpen(Session session) {
        String username = getUsername(session);
        log.info("username:{},seesionId:{}连接成功", username, session.getId());
        chatService.addSession(username, session);
    }

    @OnClose
    public void onClose(Session session) {
        try {
            String username = getUsername(session);
            chatService.removeSession(username);
        }catch (NotLoginException e) {
            log.info("not login websocket onlose event ignore!");
        }

    }
    @OnError
    public void onError(Session session, Exception e) {
        if (e instanceof NotLoginException) {
            ChatMsg chatMsg = new ChatMsg(ChatMsg.ReturnCode.NotLogin);
            chatService.replyMsg(session, chatMsg);
            try {
                session.close();
            }catch (IOException ex) {}

        }else if (e instanceof SendMsgException){
            ChatMsg chatMsg = new ChatMsg(ChatMsg.ReturnCode.Fail);
            chatMsg.setMsg(e.getMessage());
            chatService.replyMsg(session, chatMsg);
        }else {
            log.error("websocket catch error", e);
        }
    }
    @OnMessage
    public void onMessage(Session session, String strMsg) {
        String username = getUsername(session);
        log.debug("receive message:{} from username:{}", strMsg, username);
        ChatMsg chatMsg = JsonUtils.parse(strMsg, ChatMsg.class);
        chatMsg.setFromUsername(username);
        chatService.sendMsg(chatMsg);
    }


}
