package gambol.examples.classloader;

import java.util.ArrayList;

/**
 * Created by zhenbao.zhou on 15/4/9.
 */
public class LoadOrder {

    public static void main(String[] args) {


        System.out.println("----------测试引导类加载器-------------");
        System.out.println("Class的加载器:"+Class.class.getClassLoader().getSystemClassLoader());
        System.out.println("TestLoader的加载器:"+LoadOrder.class.getClassLoader());
        System.out.println("String:"+  String.class.getClassLoader());
        System.out.println("ArrayList的类加载器:"+ArrayList.class.getClassLoader());
        System.out.println("----------测试引导类加载器-------------\n");
    }
}
