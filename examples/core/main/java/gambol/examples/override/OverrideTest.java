package gambol.examples.override;

/**
 * Created by zhenbao.zhou on 16/4/12.
 */
public class OverrideTest {

    abstract static class Parent {

        void a() {
            System.out.println("parent-a");
            b();
        }

        void b() {
            System.out.println("parent-b");
        }
    }


    static class Child extends Parent {
        void b() {
            System.out.println("child-b");
        }
    }

    public static void main(String[] args) {
        Parent p = new Child();
        p.a();
    }
}
