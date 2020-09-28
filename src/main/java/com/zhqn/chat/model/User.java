package com.zhqn.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author 周全
 * @date 2020/9/28 15:55
 * @description <p>
 */
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false, unique = true)
    private String username;
    @Column(length = 32, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String name;

}
