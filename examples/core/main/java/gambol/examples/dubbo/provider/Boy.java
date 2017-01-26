package gambol.examples.dubbo.provider;

/**
 * Created by zhenbao.zhou on 16/9/14.
 */
public class Boy implements DefaultInterface {
    public int foo()  {
        System.out.println("foo");
        return 2;
    }
}
