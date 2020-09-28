package com.zhqn.chat.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @author 周全
 * @date 2020/9/28 15:58
 * @description <p>
 */

@Data
@ToString
public class LoginDto {

    @NotEmpty(message = "账号不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;

}
