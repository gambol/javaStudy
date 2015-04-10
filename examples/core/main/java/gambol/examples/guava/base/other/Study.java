package gambol.examples.guava.base.other;

import com.google.common.base.*;
import com.google.common.collect.ComparisonChain;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author kris.zhang
 */
public class Study {

    public static void testStopwatch(){
        /**
         *  有的时候我们要测量代码执行时间和任务处理时间等
         *  通用的做法是调用
         *  long start = System.currentTimeMillis();
         *  .....
         *  long end =  System.currentTimeMillis();
         *
         * */

        /** 更可读的做法是：*/
        Stopwatch stopwatch = Stopwatch.createStarted();
        //.....
        try {
            Thread.sleep(100);
        } catch (Exception e) {

        }
        stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        System.out.print(stopwatch.toString());
        /** 首先比较容易理解，其次可支持更多的功能，如时间单位等 */
    }

    public static void testObjects(){
        /**
         *  由于jdk1.7 才提供了Objects，因此guava中为了支持1.6，
         *  其也提供了相应的Objects方法。
         *  equals和toStringHelper我觉得目前项目中应用的不多，
         *  或者说我没有找到应用点。最重要的估计是hashCode了吧。
         *  当我们去判断两个实体类是否相同的时候，我们需要覆盖相应
         *  的equals方法，ej也告诉我们当覆盖equals的方法的时候，
         *  不要忘记覆盖hashCode，以告诉我们的Map，他们作为value
         *  是相等的。那如何计算，我们可以这样：
         *  之所以我们项目中很少见到覆盖equals，是因为，我们通常
         *  都会使用id去作为唯一索引，这个时候id就退化为了对象的
         *  hashCode。jdk7中的Objects有更多的用法，希望我们能
         *  够尽快支持1.7吧。
         */
        class Entity{
            Integer id;
            String name;
            @Override
            public int hashCode(){
                return Objects.hash(id,name);
            }
        }
    }

    public static void testCompareChain(){
        /** 还有一个比较常用的就是CompareChain */
        class Person implements Comparable<Person> {
            private String lastName;
            private String firstName;
            private int zipCode;

            @Override
            public int compareTo(Person other) {
                int cmp = lastName.compareTo(other.lastName);
                if (cmp != 0) {
                    return cmp;
                }
                cmp = firstName.compareTo(other.firstName);
                if (cmp != 0) {
                    return cmp;
                }
                return Integer.compare(zipCode, other.zipCode);
            }
            /** 上面引用了guava的例子 ，看起来原始的比较接口实现起来很遭心，不是么 */
            /** 其实可以这么实现 */
            public int compare(Person that){
                return ComparisonChain.start()
                    .compare(this.firstName,that.firstName)
                    .compare(this.lastName,that.lastName)
                    .compare(this.zipCode,that.zipCode)
                    .result();
            }
            /** 看起来清凉多了吧? */
        }
    }

    public static void testOthers() {
        Charset c = Charsets.UTF_8; //别使用 StandardCharsets.UTF_8 ??
        //Enums提供若干enum相关方法
        //CharMatcher.ANY;
        //提供了一些系统标准属性的key StandardSystemProperty.FILE_SEPARATOR;
    }

    public static void main(String[] args) {
        testStopwatch();
    }
}
