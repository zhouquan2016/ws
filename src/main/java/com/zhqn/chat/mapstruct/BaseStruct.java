package com.zhqn.chat.mapstruct;

/**
 * @author 周全
 * @date 2020/9/28 15:59
 * @description <p>
 */
public interface BaseStruct<D,T> {
    /**
     * dto转换为domain
     * @param t
     * @return
     */
    D dto2Domain(T t);

    /**
     * dto转换为domain
     * @param d
     * @return
     */
    T domain2Dto(D d);
}
