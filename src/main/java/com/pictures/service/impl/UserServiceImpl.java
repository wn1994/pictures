package com.pictures.service.impl;

import com.pictures.dao.UserDao;
import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;
import com.pictures.exception.BaseException;
import com.pictures.service.UserService;
import com.pictures.utils.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private static Set<UserToken> tokenSet = new HashSet<>();

    @Override
    public ResultEnum insertUser(User newUser) {
        User user = this.getUser(newUser.getPhoneNum());
        if (user == null) {
            int resCode = userDao.addUser(newUser.getPhoneNum(), newUser.getUsername(), newUser.getPassword());
            if (resCode == 1) {
                return ResultEnum.INSERT_USER_SUCCESS;
            } else {
                throw new BaseException(ResultEnum.DB_INSERT_RESULT_ERROR.getMsg());
            }
        } else {
            throw new BaseException("user already exists.");
        }
    }

    @Override
    public User getUser(String phoneNum) {
        User user = userDao.queryByPhoneNum(phoneNum);
        return user;
    }

    @Override
    public boolean checkValid(String token, String phoneNum) {
        if (tokenSet == null) {
            return false;
        }
        UserToken currentToken = new UserToken(token, phoneNum);
        for (UserToken token1 : tokenSet) {
            if (token1.equals(currentToken)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User setToken(User user) {
        /* 检测由于每次调用UserToken(String phoneNum)构造器都会生成不同的tokenId，会产生phoneNum相同但token不同的现象，
        导致同一phoneNum被重复插入tokenSet，所以在插入新的token时要删除旧的token。*/
        if (tokenSet != null && tokenSet.size() > 0) {
            Iterator<UserToken> it = tokenSet.iterator();
            while (it.hasNext()) {
                UserToken userToken = it.next();
                if (userToken.getPhoneNum().equals(user.getPhoneNum())) {
                    it.remove();
                    break;
                }
            }
        }
        UserToken token = new UserToken(user.getPhoneNum());
        tokenSet.add(token);
        user.setToken(token.getToken());
        return user;
    }


    @Override
    public void logout(String token, String phoneNum) {
        UserToken currentToken = new UserToken(token, phoneNum);
        // 删除登录条目
        if (tokenSet != null && tokenSet.size() > 0) {
            Iterator<UserToken> it = tokenSet.iterator();
            while (it.hasNext()) {
                UserToken userToken = it.next();
                if (userToken.equals(currentToken)) {
                    it.remove();
                    break;
                }
            }
        }
    }
}
