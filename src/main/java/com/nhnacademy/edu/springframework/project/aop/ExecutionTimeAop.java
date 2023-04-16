package com.nhnacademy.edu.springframework.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAop {
    @Around("execution(* com.nhnacademy.edu.springframework.project.service.*.*(..))")
    private Object timeAop(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Throwable();
        } finally {
            stopWatch.stop();
            log.info("{}.{} {}ms",
                    pjp.getTarget().getClass().getSimpleName(),
                    pjp.getSignature().getName(),
                    stopWatch.getTotalTimeMillis());
        }
    }
}
