package gambol.examples.spring.service.impl;

import gambol.examples.spring.annotation.ParamCheck;
import gambol.examples.spring.annotation.ValidatorMethod;
import gambol.examples.spring.service.TestValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 测试利用Aop进行validate校验
 * User: zhenbao.zhou
 * Date: 12/19/14
 * Time: 7:41 PM
 */

@Slf4j
@Service("testValidateService")
public class TestValidateServiceImpl implements TestValidateService {

    @ValidatorMethod("check")
    public void serviceA(int bigOne, int smallOne) {
        log.info("big one :{}, small one: {}", bigOne, smallOne);
    }

    private void check(int bigOne, int smallOne) {
        // if (objects[0] != objects[1]) throw new RuntimeException("small one is bigger than bigOne");
        log.info("check  bigOne:{} , smallOne:{}", bigOne, smallOne);
        if (bigOne < smallOne) {
            throw new RuntimeException("small one is bigger");
        }
    }

    private void check(int bigOne, int smallOne, boolean flag) {
        // will not enter this method

        if (bigOne < smallOne) {
            throw new RuntimeException("should not enter this method");
        }
    }

    public void serviceB(@ParamCheck int b) {
        log.info("hah service b");
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestValidateServiceImpl bean = (TestValidateServiceImpl)ctx.getBean("testValidateService");
        bean.serviceA(2,1);
        bean.serviceA(2,1);
        bean.serviceB(4);
    }

}

