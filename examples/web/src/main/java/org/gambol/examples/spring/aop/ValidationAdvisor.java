package org.gambol.examples.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.gambol.examples.spring.annotation.ValidatorMethod;

import java.lang.reflect.Method;

/**
 * User: zhenbao.zhou
 * Date: 12/19/14
 * Time: 7:05 PM
 */
@Slf4j
public class ValidationAdvisor {


    // Service类里的所有方法，只要带有ValidatorMethod注解的，都进入切面
    @Pointcut("execution (@org.gambol.examples.spring.annotation.ValidatorMethod * *Service*.*(..) )")
    private void validate() {}

    @Before("validate()")
    public void validatePoint(Method method, JoinPoint joinPoint) throws Throwable {

        Class<?> clazz = method.getClass();
        String className = clazz.getName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        ValidatorMethod validatorMethod = method.getAnnotation(ValidatorMethod.class);
        String validateMethodName = validatorMethod.value();

        log.info("runing to validate ({}), validateMethod is {}, args={}", className, methodName,
          validateMethodName,
          args);

        Method validateProcess = clazz.getMethod(validateMethodName, Object[].class);

        // 如果有问题，那么需要在这里面抛出异常
        validateProcess.invoke(args);
    }

}
