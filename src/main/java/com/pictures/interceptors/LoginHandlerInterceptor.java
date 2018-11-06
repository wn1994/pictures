package com.pictures.interceptors;

import com.pictures.aop.ServiceRedisAop;
import com.pictures.entity.User;
import com.pictures.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceRedisAop.class);
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        // 访问登录和注册页面以及静态资源页面直接放行
        String pattern = ".*(.html|.js|.css|.jpg|.png|.gif|.ps|.jpeg)$";
        if (StringUtils.equals(requestURI, "/sign") || StringUtils.equals(requestURI, "/signup") ||
                StringUtils.equals(requestURI, "/signin") || Pattern.matches(pattern, requestURI)) {
//            LOG.info("requestURI:" + requestURI);
            return true;
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            // 如果有session，说明已登录，放行
            if (user != null) {
                return true;
            } else {
                // session不存在的情况下，如果cookies通过验证，自动登录
                String token = "";
                String phoneNum = "";
                Cookie[] cookies = request.getCookies();
                // 如果cookies不存在，那么跳转登录页
                if(cookies == null){
                    response.sendRedirect("/sign");
                    return false;
                }
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                    } else if ("phoneNum".equals(cookie.getName())) {
                        phoneNum = cookie.getValue();
                    }
                }
                if (this.userService.checkValid(token, phoneNum)) {
                    user = this.userService.getUser(phoneNum);
                    session.setAttribute("user", user);
                    return true;
                }
            }
        }
        // 全部不符合，跳转到登录页面
        response.sendRedirect("/sign");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object
            handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object
            handler, Exception ex) throws Exception {

    }
}
