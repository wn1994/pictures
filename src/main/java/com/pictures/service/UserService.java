package com.pictures.service;

import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;

/**
 * 业务接口：站在"使用者"角度设计接口 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface UserService {
    ResultEnum insertUser(User newUser);

    User getUser(String phoneNum);

    boolean checkValid(String token,String phoneNum);

    User setToken(User user);

    void logout(String token,String phoneNum);
}
