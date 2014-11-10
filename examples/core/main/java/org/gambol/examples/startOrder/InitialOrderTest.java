package org.gambol.examples.startOrder;

/**
 * User: zhenbao.zhou
 * Date: 10/21/14
 * Time: 10:08 PM
 */
public class InitialOrderTest {

    // 静态变量
    public static String staticField = "静态变量";

    public static InitialOrderTest initialOrderTest = new InitialOrderTest("class构造期  ");

    public String prefix;

    // 变量
    public String field = "变量";

    // 初始化块
    {
        System.out.println(prefix + field);
        System.out.println(prefix + "初始化块");
    }

    // 静态初始化块
    static {
        System.out.println(staticField);
        System.out.println("静态初始化块");
    }


    // 构造器
    public InitialOrderTest(String prefix) {
        this.prefix = prefix;
        System.out.println(prefix + " 构造器");
    }


}