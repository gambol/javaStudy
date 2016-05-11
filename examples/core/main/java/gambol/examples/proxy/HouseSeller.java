package gambol.examples.proxy;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhenbao.zhou on 16/3/5.
 */
public interface HouseSeller {

    boolean sell(int money, int peopleId);

    void signContract(int peopleId);

}

class $Young implements HouseSeller {

    @Override
    public boolean sell(int money, int peopleId) {

        if (money > 100) {
            System.out.println("ok, deal");
            return true;
        } else {
            System.out.println("no, i want more");
            return false;
        }
    }

    @Override
    public void signContract(int peopleId) {
        System.out.println("done. bye bye");
    }
}

class $Old implements HouseSeller {

    $Young young;

    public $Old($Young young) {
        this.young = young;
    }

    @Override
    public boolean sell(int money, int peopleId) {
        // 代理人替实体干活
        if (peopleId < 0) {
            System.out.printf("no, not a good man");
            return false;
        }

        return young.sell(money, peopleId);
    }

    @Override
    public void signContract(int peopleId) {
        // 找律师, 找房产证

        young.signContract(peopleId);
    }
}


class Handler implements InvocationHandler {

    $Young young;

    public HouseSeller bind($Young young) {
        this.young = young;
        // 重点关注
        return (HouseSeller) Proxy.newProxyInstance(young.getClass().getClassLoader(), young.getClass().getInterfaces(), this);
    }

    // 重点关注
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        System.out.println("I am proxy: kakaka");

        return method.invoke(young, args);
    }


    // 真正调用的地方
    public static void main(String[] args) {
        Handler old = new Handler();

        HouseSeller seller = old.bind(new $Young());

        int peopleId = 3;
        int money = 200;
        if (seller.sell(money, peopleId)) {
            seller.signContract(peopleId);
        }
    }
}

class Hacker implements MethodInterceptor {

    //要代理的原始对象
    private Object obj;

    public Object createProxy(Object target) {
        this.obj = target;
        // cglib里的增强类
        Enhancer enhancer = new Enhancer();

        // 设置代理目标
        enhancer.setSuperclass(this.obj.getClass());

        // 设置回调. 这是相当于代理类上的所有方法, 都会使用这个callback. callback使用intercept进行拦截
        enhancer.setCallback(this);

        return enhancer.create();
    }


    public Object intercept(Object paramObject, Method paramMethod, Object[] paramArrayOfObject, MethodProxy paramMethodProxy)
            throws Throwable {
        System.out.println("I am hacker go");
        Object o = paramMethodProxy.invokeSuper(paramObject, paramArrayOfObject);
        return o;
    }

    public static void main(String[] args) {
        $Young proxyYoung =  ($Young)new Hacker().createProxy(new $Young());

        int peopleId = 3;
        int money = 200;
        if (proxyYoung.sell(money, peopleId)) {
            proxyYoung.signContract(peopleId);
        }
    }
}
