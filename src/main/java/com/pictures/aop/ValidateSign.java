package com.pictures.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Aspect
@Component
public class ValidateSign {
    @Pointcut("(execution(* com.pictures.controller.SignController.processSignIn(..)) || " +
            "execution(* com.pictures.controller.SignController.processSignUp(..)))")
    public void signPoint() {
    }
}
