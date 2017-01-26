package gambol.examples.dubbo.provider;

import java.io.Serializable;

/**
 * Created by zhenbao.zhou on 16/9/14.
 */
public interface DefaultInterface extends Serializable {

    String  x = "xxx";

    int foo();

    default int bar() {
        System.out.println("haha, bar, foo()" +  foo());
        return foo();
    }
}
