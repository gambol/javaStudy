package java.lang;

/**
 * Created by zhenbao.zhou on 15/4/9.
 *
 * 系统class的加载策略是 先执行 bootstrapClass loader， 然后执行 ext class loader， 最后执行 app class loader。
 *
 * 更高级的说法是：
 * 为什么只加载系统通过的java.lang.String类而不加载用户自定义的java.lang.String类呢？

 因加载某个类时，优先使用父类加载器加载需要使用的类。如果我们自定义了java.lang.String这个类，
 加载该自定义的String类，该自定义String类使用的加载器是AppClassLoader，根据优先使用父类加载器原理，
 AppClassLoader加载器的父类为ExtClassLoader，所以这时加载String使用的类加载器是ExtClassLoader，
 但是类加载器ExtClassLoader在jre/lib/ext目录下没有找到String.class类。然后使用ExtClassLoader父类的加载器BootStrap，
 父类加载器BootStrap在JRE/lib目录的rt.jar找到了String.class，
 *
 */
public class String2{
    int a ;

}
