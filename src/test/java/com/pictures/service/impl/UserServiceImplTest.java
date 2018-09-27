package com.pictures.service.impl;

import com.pictures.BaseTest;
import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;
import com.pictures.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserServiceImplTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void testInsertUser() throws Exception {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        User user = new User(1, "张三san", "wn123", date, "18810000004");
        System.out.println("testInsertUser");
        ResultEnum result = userService.insertUser(user);
        System.out.println(result == ResultEnum.INSERT_USER_SUCCESS);
    }

    @Test
    public void testGetUser() throws Exception {
        String phoneNum = "18810000000";
        User user = userService.getUser(phoneNum);
        System.out.println("testGetUser");
        System.out.println(user);
    }
}
