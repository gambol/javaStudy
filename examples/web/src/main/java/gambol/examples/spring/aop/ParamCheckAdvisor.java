package gambol.examples.spring.aop;

import gambol.examples.spring.annotation.ValidatorMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
public class ParamCheckAdvisor {

//
//    // Service类里的所有方法，只要带有ValidatorMethod注解的，都进入切面
//    @Pointcut("execution (* *(@gambol.examples.spring.annotation.ParamCheck (*))")
//    public void before() {  }
//
//    @Before("before()")
//    public void validatePoint(JoinPoint joinPoint) throws Exception {
//        log.info("hahah before gogogogo");
//    }


}
