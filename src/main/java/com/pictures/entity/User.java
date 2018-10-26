package com.pictures.entity;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Data
public class User {
    private long id;
    @Size(min = 1, max = 20)
    private String username;
    @Size(min = 1, max = 20)
    private String password;
    @Size(min = 11, max = 11)
    private String phoneNum;
    private String token;
    private Date registerTime;

    public User() {
        super();
    }

    public User(long id, String username, String password, Date registerTime, String phoneNum) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.registerTime = registerTime;
        this.phoneNum = phoneNum;
    }
}
