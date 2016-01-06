package gambol.examples.lambda;

import java.util.List;

/**
 * Created by zhenbao.zhou on 15/11/1.
 */
@FunctionalInterface
interface Teacher {
    void teach();

    default boolean ask (String name) {
        System.out.println(name + " 你来回答一下这个问题");
        // ....
        return false;
    }

    static void build(List<String> students) {

    }
}
