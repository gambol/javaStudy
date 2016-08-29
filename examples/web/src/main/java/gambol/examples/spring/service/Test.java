package gambol.examples.spring.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhenbao.zhou on 16/6/22.
 */
@Service
public class Test {

    // @Resource
    // ConsoleService consoleService;

    public static void main(String[] args) {
//        EmptyInterface e = new EmptyInterface() ;
//
//        say1=(Say)JDKDynamicProxy.createProxy(say1);

    }

    public void job() {
        System.out.println(" dajiahao , wo shi liudehua ");
        System.out.println(new Date());
    }
}
