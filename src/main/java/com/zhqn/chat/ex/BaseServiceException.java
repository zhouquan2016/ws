package com.zhqn.chat.ex;


public class BaseServiceException extends RuntimeException{

    public BaseServiceException(String msg) {
        super(msg);
    }

    public static BaseServiceException throwOne(String msg) {
        throw new BaseServiceException(msg);
    }

    public static void assertOne(boolean expectTrue, String msg) {
        if (!expectTrue) {
            throwOne(msg);
        }
    }
}
