package org.gambol.examples.dubbo.consumer;

import org.gambol.examples.dubbo.provider.DubboService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * User: zhenbao.zhou
 * Date: 11/18/14
 * Time: 5:25 PM
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
          new String[] { "dubbo-consumer.xml" });
        context.start();

        DubboService demoService = (DubboService) context.getBean("dubboService"); //
        String hello = demoService.sayHello("tom"); // ִ
        System.out.println(hello); //

        //
        List list = demoService.getUsers();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
        // System.out.println(demoService.hehe());
        System.in.read();
    }
}
