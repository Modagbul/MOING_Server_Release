package com.moing.backend.global.log.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {


    @Pointcut("execution(* com.moing.backend.*presentation.*Controller.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.moing.backend..service..*.*(..))")
    public void allService() {
    }

}
