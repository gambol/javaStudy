package gambol.examples.extendtest;

/**
 * Created by zhenbao.zhou on 17/2/10.
 */
public abstract class Base {

    public void a() {
        System.out.println("base a. this:" + this);

        this.b();
    }

    public void b() {
        System.out.println("base b");
    }
}
