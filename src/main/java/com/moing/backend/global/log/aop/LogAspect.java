package com.moing.backend.global.log.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogTrace logTrace;

    @Around("com.moing.backend.global.log.aop.Pointcuts.allService()")
    public Object serviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    @Around("com.moing.backend.global.log.aop.Pointcuts.allController()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return getObject(joinPoint);
    }

    private Object getObject(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.start(joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            Object result = joinPoint.proceed();
            logTrace.end(traceStatus);
            return result;
        }catch (Exception e) {
            if (traceStatus != null) {
                logTrace.exception(e, traceStatus);
            }
            throw e;
        }
    }
}