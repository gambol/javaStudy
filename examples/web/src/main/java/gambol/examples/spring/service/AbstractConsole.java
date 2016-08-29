package gambol.examples.spring.service;

/**
 * Created by zhenbao.zhou on 16/6/22.
 */
public abstract class AbstractConsole implements Console {

    public abstract void test();

    public void console() {
        System.out.println("in abstract console");
    }
}
