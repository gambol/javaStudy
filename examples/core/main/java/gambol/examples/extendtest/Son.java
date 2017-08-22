package gambol.examples.extendtest;

/**
 * Created by zhenbao.zhou on 17/2/10.
 */
public class Son extends Base {

    public void a() {
        System.out.println("son a. this:" + this);
        super.a();
    }

    public void b() {
        System.out.println("son b");
    }

    public static void main(String[] args) {
        new Son().a();

    }
}
