package com.bridgelabz.lmsapi.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class AopConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(value = "execution(* com.bridgelabz.lmsapi.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("Before method ");
        logger.info("Allowed execution for {}", joinPoint);
    }

    @Around("execution(* com.bridgelabz.lmsapi.service.*.*(..))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution details
        logger.info("Execution details " + className + "." + methodName + " " + ":: " + result +
                + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }

    @After(value = "execution(* com.bridgelabz.lmsapi.service.*.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("After method ");
        logger.info("After execution of {}", joinPoint);
    }

    @AfterReturning(value = "execution(* com.bridgelabz.lmsapi.service.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.info("{} returned with value {}", joinPoint, result);
    }

}
