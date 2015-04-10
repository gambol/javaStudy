package gambol.examples.guava.base.check;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 参数检查
 *
 * Kind of check	     The throwing method is saying...
 * Precondition	         "You messed up (caller)."
 * Assertion	         "I messed up."
 * Verification	         "Someone I depend on messed up."
 * Test assertion	     "The code I'm testing messed up."
 * Impossible condition	 "What the? the world is messed up!"
 * Exceptional result    "No one messed up, exactly (at least in this VM)."
 *
 * @author kris.zhang
 */
public class Study {

    public static void testCheck(){
        /**
         *  base包里的参数检查有如下几个常用的类：
         *  Preconditions和Verify，他们基本上是差不多的，不过
         *  上面的表格已经说明了区别。Verify会抛出VerifyException
         *  而Preconditions是不会的
         */
        /* 空指针异常 */
        Preconditions.checkNotNull(null,"msg");

        /* IllegalArgumentException */
        Preconditions.checkArgument(false,"msg");

        /* IndexOutOfBoundsException */
        Preconditions.checkElementIndex(2,10/*list.size()*/,"msg");

        /* IndexOutOfBoundsException */
        Preconditions.checkPositionIndex(2,10/* array.length */,"msg");

        /* IllegalStateException 检查类状态*/
        Preconditions.checkState(false);

        /*VerifyException 他是RuntimeException*/
        //Verify.verify(false,"msg");
        //Verify.verifyNotNull(null,"msg");

        /*
        * ps.
        * msg都封装到了exception信息里
        * 我们在controller也可以使用
        * 不过我们要返回相应格式的json串儿，我在异常处理的wiki会进行相应的
        * 讨论
        */
    }

    public static void testOptional(){
        /** 为啥不用null，引用一个文档：
        * We would like to emphasize that these methods are primarily
        * for interfacing with unpleasant APIs that equate null strings
        * and empty strings. Every time you write code that conflates
        * null strings and empty strings, the Guava team weeps. (If
        * null strings and empty strings mean actively different things
        * , that's better, but treating them as the same thing is a
        * disturbingly common code smell.)
        * 然后自行去google看看巴。
        */

        /** 主要是解决以下几个问题：
        *  （1）函数输入，我们可以使用参数检查和@Nullable注解
        *  （2）函数输出，迫使我们思考NULL，就是wrapper了NULL
        *  （3）集合类，不支持NULL输入的封装
        *  （4）Map,用来区分不存在map中和存在map中但是只缺失。想
        *  一想当从map获得一个null的时候我们能判断上面吗？如果追加了
        *  optional，我们就知道他是缺失的。如果返回了null就表示不存在
        *  如果返回optional.absent()==false，那么就表示缺失。
        */
        /** 我们要常用@Nullable注解以提示输入参数是否支持null */
        Optional<String> optional = Optional.of(new String(""));
        Optional.fromNullable(null/** or object */);

        if(optional.isPresent()/** 没有optional.isAbsent()，我们需要取反 判断*/){
        }

        optional.get();/* get object */
        optional.or(new String("default")/* default value if absent */);

        /** '函数编程'形式*/
        optional.or(new Supplier<String>() {
            @Override public String get() {
                return "default";
            }
        });

        optional.orNull();/** 不存在就返回null */
        /** 可以看到optional相当的灵活 */
    }


    public static void main(String[] args) {
        testOptional();

    }
}
