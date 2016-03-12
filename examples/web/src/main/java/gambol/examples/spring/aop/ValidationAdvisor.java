package gambol.examples.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import gambol.examples.spring.annotation.ValidatorMethod;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * User: zhenbao.zhou
 * Date: 12/19/14
 * Time: 7:05 PM
 */
@Slf4j
@Component
@Aspect
public class ValidationAdvisor {


    // Service类里的所有方法，只要带有ValidatorMethod注解的，都进入切面
    @Pointcut("execution (@gambol.examples.spring.annotation.ValidatorMethod * *(..))")
    public void before() {  }

    @Before("before()")
    public void validatePoint(JoinPoint joinPoint) throws Exception {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String className = clazz.getName();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        ValidatorMethod validatorMethod = method.getAnnotation(ValidatorMethod.class);
        if (validatorMethod == null) {
            return;
        }
        String validateMethodName = validatorMethod.value();

        log.info("runing to validate ({}, {}), validateMethod is {}, args={}", className, methodName,
          validateMethodName,
          args);

        Method validateProcess = clazz.getDeclaredMethod(validateMethodName, method.getParameterTypes());
        if (validateProcess == null) {
            log.warn("cannot find target validate Process. skip validate");
            return;
        }
        validateProcess.setAccessible(true);

        Object o = joinPoint.getTarget();
        // 如果有问题，那么需要在这里面抛出异常
        validateProcess.invoke(o,args);
    }


}
