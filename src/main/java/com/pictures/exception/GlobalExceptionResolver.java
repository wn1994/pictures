package com.pictures.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("访问"+request.getRequestURI()+"发生错误，错误信息："+ex.getMessage());

        LOG.error("访问"+request.getRequestURI()+"发生错误，错误信息："+ex.getMessage());
        ModelAndView error = new ModelAndView("error");
        error.addObject("exMsg",ex.getMessage());
        error.addObject("exType", ex.getClass().getSimpleName().replace("\"", "'"));
        return error;
    }
}
