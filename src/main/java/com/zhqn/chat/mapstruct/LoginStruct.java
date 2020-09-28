package com.zhqn.chat.mapstruct;

import com.zhqn.chat.dto.LoginDto;
import com.zhqn.chat.model.User;
import org.mapstruct.Mapper;

/**
 * @author EDZ
 */
@Mapper(componentModel = "spring")
public interface LoginStruct extends BaseStruct<User, LoginDto>{
}
