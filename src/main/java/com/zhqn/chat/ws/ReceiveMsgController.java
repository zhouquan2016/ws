package com.zhqn.chat.ws;

import com.zhqn.chat.service.ChatService;
import com.zhqn.chat.ws.dto.ChatMsg;
import com.zhqn.chat.ws.dto.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 周全
 * @date 2020/9/27 14:57
 * @description <p>
 */
@Controller
@Slf4j
@RequestMapping("/private")
public class ReceiveMsgController {

    @Autowired
    ChatService chatService;

    @RequestMapping("/receiveMsg")
    public ChatMsg send(ChatMsg chatMsg) {
        ChatMsg responseMsg = null;
        try {
            chatService.receiveMsg(chatMsg);
            responseMsg = new ChatMsg(ChatMsg.ReturnCode.Success);
        }catch (Exception e) {
            responseMsg = new ChatMsg(ChatMsg.ReturnCode.Fail);
            responseMsg.setMsg(e.getMessage());
            log.error("receiveMsg eror", e);

        }
        return responseMsg;
    }
}
