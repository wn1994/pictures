package com.pictures.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class validateSign {
    @Pointcut("(execution(* com.pictures.controller.SignController.processSignIn(..)) || " +
            "execution(* com.pictures.controller.SignController.processSignUp(..)))")
    public void SignPoint(){}

//    @Around("SignPoint()")
    public Object checkValidateLogin(ProceedingJoinPoint pjd) throws Throwable {
        Object[] obj = pjd.getArgs();
        System.out.println("obj0"+obj[0]+"obj1"+obj[1]);
        if (obj[0] == null || obj[1] == null){
            return -1;
        }

        if(((String)obj[0]).equals("") || ((String)obj[1]).equals("")){
            return -1;
        }
        Object result = pjd.proceed();
        return 1;
    }
}

