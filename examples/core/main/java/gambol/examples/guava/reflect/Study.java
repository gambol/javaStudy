package gambol.examples.guava.reflect;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.reflect.*;
import com.google.common.reflect.Parameter;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.List;

import static com.google.common.reflect.ClassPath.ClassInfo;

/**
 * 一些常用的反射工具
 *
 * @author kris.zhang
 */
public class Study {

    interface FooI {
        public void fun();
    }

    /**
     * 只说这一个，看 commons 的 ReflectUtil用的这个
     * 比jdk多了一个classpath，他能找到所有class path中的类
     * 用来找到某一个包下的所有类，这个很方便
     */
    public static void testClassPath() {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            classPath.getTopLevelClasses(); //该方法不会递归的执行！只找顶层的类
            ImmutableSet<ClassInfo> info = classPath.getTopLevelClassesRecursive("gambol.examples");
            for (ClassInfo classInfo : info) {
                System.out.println(classInfo.getName());
                System.out.println("===========");
                //装载class
                Class clazz = classInfo.load();
                //进行额外的处理
                System.out.println("===========");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testRelectionUtil() {
        /**
         * 工具方法Reflection.initialize(Class…)能够确保特定的类被初始化——执行任何静态初始化。
         * 使用这种方法的是一个代码异味，因为静态伤害系统的可维护性和可测试性。在有些情况下，你别无
         * 选择，而与传统的框架，操作间，这一方法有助于保持代码不那么丑。
          */
        Reflection.initialize();//批量加载每个类
        System.out.println(Reflection.getPackageName(Object.class));

        class Foo implements FooI{
            @Override public void fun() { System.out.println("target"); }
        }

        class Handler implements InvocationHandler{

            Object target;
            Handler(Object target) {
                this.target = target;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                System.out.println("invoke");
                return method.invoke(this.target,args);
            }
        }

        /** 可以看到以前的动态代理相当的麻烦 */
        FooI foo1 = (FooI) Proxy.newProxyInstance(
                Foo.class.getClassLoader(),
                new Class<?>[] { FooI.class },
                new Handler(new Foo())
        );
        foo1.fun();

        /* 有更加方便和安全的动态代码方式 */
        FooI foo2 = Reflection.newProxy(FooI.class, new AbstractInvocationHandler(){
            //带有hashCode和equals以及toString功能
            @Override
            protected Object handleInvocation(Object proxy, Method method, Object[] args)
                    throws Throwable {
                System.out.println("lala nihao");
                return null;
            }
        });


        foo2.fun();

        FooI foo3 = Reflection.newProxy(FooI.class, new Handler(new Foo()));
        foo3.fun();
    }

    public static void testInvokable() throws NoSuchMethodException {
        /**
         * Guava的Invokable是对java.lang.reflect.Method
         * 和java.lang.reflect.Constructor的流式包装。它简化了常见的反射代码的使用
        */
        Invokable invokable = Invokable.from(Study.class.getMethod("testInvokable"));
        invokable.isPublic();
        invokable.isPackagePrivate();
        invokable.isOverridable();
        Parameter parameter = (Parameter) invokable.getParameters().get(0);
        parameter.isAnnotationPresent(Override.class);
        try {
            //调用
            invokable.invoke(new Object(),null,null,null,null,null,null,null);//参数
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guava提供了TypeToken, 它使用了基于反射的技巧甚至让你在运行时都能够巧妙的操作和查询泛型类型。
     */
    public static void testType() {
        List<String> list = Lists.newArrayList();
        for (TypeVariable typeVariable : list.getClass().getTypeParameters()) {
            System.out.println(typeVariable.getName());
        }
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);
    }

}
