package gambol.examples.foreach;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenbao.zhou on 17/4/5.
 */
public class Foreach {

    static class Inner {
        int age;
        String name;

        public Inner(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Inner{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private List<Inner> kk() {
        System.err.println("hahah kk");

        int a = 0;
        int b = 1;
        int c = 2;
        System.err.println("hahah kk2");

        Inner i1 = new Inner(4, "a");
        Inner i2 = new Inner(4, "b");
        Inner i3 = new Inner(4, "c");

        List l =  new ArrayList<>();
        l.add(i1);
        l.add(i2);
        l.add(i3);

        return l;
    }

    public static void main(String[] args) {
        new Foreach().kk().forEach(i -> {
            System.err.println("aaaa:" + i);
        });
    }
}
