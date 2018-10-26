package com.pictures.dao;

import com.pictures.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

public interface UserDao {
    /**
     * 根据手机号查询用户
     *
     * @param phoneNum
     * @return
     */
    User queryByPhoneNum(@Param("phoneNum") String phoneNum);

    /**
     * 插入新用户
     *
     * @param phoneNum
     * @param username
     * @param password
     * @return
     */
    int addUser(@Param("phoneNum") String phoneNum, @Param("username") String username, @Param("password") String password);
}
