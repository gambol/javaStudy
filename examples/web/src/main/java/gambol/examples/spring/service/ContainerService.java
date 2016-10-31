package gambol.examples.spring.service;

import lombok.Setter;

/**
 * Created by zhenbao.zhou on 16/6/22.
 */
public class ContainerService {

    @Setter
    AbstractConsole abstractConsole;

    public void callConsole() {
        System.out.println(abstractConsole.getClass());
        System.out.println(abstractConsole.getClass().getPackage());
        Class c = abstractConsole.getClass();
        Class d = c.getSuperclass();

        abstractConsole.test();
    }
}
