package org.gambol.examples.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: zhenbao.zhou
 * Date: 11/18/14
 * Time: 4:36 PM
 */
public class ProviderMain {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"dubbo-provider"
          + ".xml"});
        context.start();

        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
