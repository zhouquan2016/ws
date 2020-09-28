package com.zhqn.chat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 周全
 * @date 2020/9/28 10:54
 * @description <p>
 */
public class JsonUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
