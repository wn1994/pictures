package com.pictures.dao;

import com.pictures.BaseTest;
import com.pictures.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseTest {

    //    在spring-dao.xml中所有的dao接口都被动态实现并注入到spring中
    @Autowired
    private UserDao userDao;

    @Test
    public void testQueryByPhoneNum() throws Exception {
        String phoneNum = "18810000001";
        User user = userDao.queryByPhoneNum(phoneNum);
        System.out.println(user);
    }

    @Test
    public void testAddUser() throws Exception {
        String phoneNum = "18810000002";
        String username = "王五";
        String password = "wnwnwn";
        int rescode = userDao.addUser(phoneNum, username, password);
        System.out.println(rescode);
    }
}
