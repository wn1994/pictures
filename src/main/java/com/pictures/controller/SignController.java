package com.pictures.controller;

import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;
import com.pictures.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Controller
public class SignController {
    private static final Logger LOG = LoggerFactory.getLogger(SignController.class);
    private UserService userService;

    public SignController() {
        super();
    }

    /**
     * spring推荐在构造器中引入依赖
     */
    @Autowired
    public SignController(UserService userService) {
        super();
        this.userService = userService;
    }

    /**
    * 选择注册或登录页面
    *
    * @return jsp页面
     */
    @RequestMapping(value = "/sign")
    public String sign() {
        return "sign";
    }

    @RequestMapping(value = "/signup", method = GET)
    public String showSignUp() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = POST)
    public String processSignUp(@Valid User user, BindingResult errors, HttpServletResponse response) {
        if (errors.hasErrors()) {
            LOG.info("格式有误!");
            return "signup";
        }
        ResultEnum result = this.userService.insertUser(user);
        if (result == ResultEnum.INSERT_USER_SUCCESS) {
            return "redirect:/signin";
        } else {
            return "signup";
        }
    }

    @RequestMapping(value = "/signin", method = GET)
    public String showSignIn() {
        return "signin";
    }

    @RequestMapping(value = "/signin", method = POST)
    public String processSignIn(@Valid User userSign, BindingResult errors, HttpServletResponse response, HttpSession session) {
        if (errors.hasErrors()) {
            LOG.info("格式有误!");
            return "signin";
        }
        User user = this.userService.getUser(userSign.getPhoneNum());
        if (user != null) {
            if (user.getPassword().equals(userSign.getPassword())) {
                user = this.userService.setToken(user);
                int cookieAge = 60 * 60 * 10;
                Cookie cookie1 = new Cookie("phoneNum", user.getPhoneNum());
                cookie1.setHttpOnly(true);
                cookie1.setMaxAge(cookieAge);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("token", user.getToken());
                cookie2.setHttpOnly(true);
                cookie2.setMaxAge(cookieAge);
                response.addCookie(cookie2);

                session.setAttribute("user", user);
                return "forward:/";
            }
        }
        return "signin";
    }

    @RequestMapping(value = "/logout")
    public String logOut(@CookieValue("token") String token,
                         @CookieValue("phoneNum") String phoneNum, HttpServletResponse response, HttpSession session) {
        Cookie cookie = new Cookie("token", token);
        // 令token失效
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        this.userService.logout(token, phoneNum);
        session.invalidate();
        return "redirect:/sign";
    }


    @RequestMapping("/")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return "redirect:/user/" + user.getPhoneNum() + "/picture";
    }
}