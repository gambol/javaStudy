package org.gambol.examples.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.net.MalformedURLException;

/**
 * User: zhenbao.zhou
 * Date: 11/18/14
 * Time: 8:12 PM
 */
public class HessianClient {
    public static void main(String[] args) throws MalformedURLException {
        //Spring Hessian代理Servelet
        String url = "http://localhost:9993/helloSpring";
        HessianProxyFactory factory = new HessianProxyFactory();
        HessianService api = (HessianService) factory.create(HessianService.class, url);

        System.out.println(api.sayHello("gambol"));
    }
}
