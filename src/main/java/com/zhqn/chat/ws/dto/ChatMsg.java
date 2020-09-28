package com.zhqn.chat.ws.dto;

import lombok.Data;

/**
 * @author 周全
 * @date 2020/9/27 13:56
 * @description <p>
 */
@Data
public class ChatMsg {
    private String code;
    private String fromUsername;
    private String toUsername;
    private String msg;

    public boolean isSuccess () {
        return ReturnCode.Success.code.equals(this.code);
    }

    public ChatMsg() {

    }
    public ChatMsg(ReturnCode returnCode) {
        this.code = returnCode.code;
        this.msg = returnCode.msg;
    }

    public  enum ReturnCode{

        NotLogin("1", "未登陆"),
        Success("2", "成功"),
        Fail("3", "失败");

        public String code;
        public String msg;
        ReturnCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
