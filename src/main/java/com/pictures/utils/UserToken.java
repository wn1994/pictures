package com.pictures.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * 生成用户session所需的token
 *
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Getter
@Setter
public class UserToken {
    private String token;
    private String phoneNum;

    public UserToken(String phoneNum) {
        super();
        this.phoneNum = phoneNum;
        this.token = UUID.randomUUID().toString().replace("-", "");
    }

    public UserToken(String token, String phoneNum) {
        super();
        this.token = token;
        this.phoneNum = phoneNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserToken) {
            UserToken anotherToken = (UserToken) obj;
            return this.token.equals(anotherToken.getToken()) && this.phoneNum.equals(anotherToken.getPhoneNum());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return phoneNum.hashCode() + token.hashCode();
    }
}
