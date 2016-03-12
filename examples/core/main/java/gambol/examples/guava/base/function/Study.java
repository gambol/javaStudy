package gambol.examples.guava.base.function;

import com.google.common.base.*;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * 如果你不是使用函数编程的形式，那么就忽略这些吧
 * 三大特性：
 *  immutable data
 *  first class functions
 *  尾递归优化
 *
 *  几个技术
 *  map & reduce
 *  pipeline
 *  recursing
 *  currying
 *  higher order function
 *
 *  好处
 *  parallelization
 *  lazy evaluation
 *  determinism
 *
 *  http://coolshell.cn/articles/10822.html
 *  http://www.ibm.com/developerworks/cn/java/j-ft20/
 *
 * @author kris.zhang
 */
public class Study {
    public static void main(String[] args) {

        /**
         * guava为了模拟函数编程中的函数，使用了三个很重要的接口：
         * Function
         * Predicate
         * Supplier
         */

        /** 通用函数，f(a)=b */
         new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };

        /** 第二个表示返回值是boolean的函数，表示一种判断，一种取舍 */
        new Predicate<String>(){
            @Override
            public boolean apply(@Nullable String input) {
                return input.length() > 0 ;
            }
        };

        /** 表示一种提供者，类似于provider或Factory 如ThreadFactory */
        new Supplier<String>() {
            @Override public String get() {
                return null;
            }
        };

        /** 下面看一下使用function */
        Collections2.transform(Ints.asList(1, 2, 3), new Function<Integer, Integer>() {
            @Nullable @Override
            public Integer apply(@Nullable Integer input) {
                return input*input;
            }
        });

        /** 下面看一下使用predicate */
        Collections2.filter(Ints.asList(1, 2, 3, 4, 121, 122), new Predicate<Integer>() {
            @Override public boolean apply(@Nullable Integer input) {
                return input > 100;
            }
        });

        /** 下面看一下使用Supplier */
        CacheLoader.from(new Supplier<Object>() {
            @Override
            public Object get() {
                return null;//from db
            }
        });


        /** 为了方便，有些通用的Supplier Function 和 Predicate guava已经帮你实现了，他们放在后缀s的类中 */
        Function<Object,String> f1 = Functions.constant("hello world");//h(n)="hello world"
        Function f2 = Functions.compose(Functions.constant("hello"),Functions.constant("world"));//h(a) == g(f(a))
        Functions.forMap(ImmutableMap.of("key","value")).apply("key");//=>value
        Functions.forPredicate(null);//predicate->function<T, Boolean> 调用apply 实际是调用predicate的apply
        Functions.forSupplier(null);//supplier->function<Object, T> 调用apply 实际是调用supplier的get

        Predicates.alwaysFalse();
        Predicates.alwaysTrue();
        Predicates.and(Predicates.alwaysFalse(), Predicates.alwaysTrue());//=>false
        Predicates.or(Predicates.alwaysFalse(), Predicates.alwaysTrue());//=>true
        Predicates.not(Predicates.alwaysFalse());//=>true
        Predicates.instanceOf(Object.class);//=>true for every object
        Predicates.in(Ints.asList(1, 2, 3));//predicate.apply(1)=>true predicate.apply(11)=>false
        Predicates.containsPattern("^hello$");//predicate.apply("hello")=>true
        Predicates.notNull();
        Predicates.isNull();
        Predicates.equalTo("compare");//predicate.apply("compare")=>true
        Predicates.compose(Predicates.alwaysFalse(),Functions.constant("hello world"));//p.apply(f.apply(a))

        Suppliers.ofInstance(new Object());//永远是该对象
        Suppliers.compose(Functions.constant("hello world"), Suppliers.ofInstance(new Object()));//function.apply(supplier.get())
        Suppliers.memoize(Suppliers.ofInstance(new Object()));//讲object缓存起来。即只调用1get进行付值操作
        Suppliers.memoizeWithExpiration(Suppliers.ofInstance(new Object()),1, TimeUnit.DAYS);//一天过期
        Suppliers.supplierFunction().apply(Suppliers.ofInstance(new Object()));//输入supplier输出supplier.get()
        Suppliers.synchronizedSupplier(Suppliers.ofInstance(new Object()));//同步supplier.get方法
    }
}
