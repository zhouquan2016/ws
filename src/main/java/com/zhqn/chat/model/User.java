package com.zhqn.chat.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 周全
 * @date 2020/9/28 15:55
 * @description <p>
 */
@Data
@Table
@Entity
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
    @Version
    private Long version;

}
