package gambol.examples.guava.base.string;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * base包里关于Strings最常用的就是Spliter和Joiner
 *
 * @author kris.zhang
 */
@Slf4j
public class Study {

    private static void testStrings() {

        /** 用来代替：if(string == null || 用string.isEmpty()还是string.length() == 0 ？) */
        Strings.isNullOrEmpty(""/* null is also ok */);

        /** 当字符串的null表示一个明确的状态时候，我们才使用 */
        Strings.emptyToNull("");

        /**
         * 常用，我们不希望再代码中经常看到字符串是否为null的判断，当我们
         * 约束了字符串可以为empty的时候，我们还需要再进行null的判断，这个
         * 其实是很恶心的东西,比如我们从数据库或缓存拿数据的时候，用他进行
         * 一次包装，就可以得到一个非null的字符串，外面调用者使用就不用考虑
         * 是否为null的情况了，可以减少if null的判断
         * */

        String emptyString = Strings.nullToEmpty(null);

        /* 这个可以用来代替如下代码 */
        StringBuilder b = new StringBuilder();
        b.append("xx").append("xx");
        Strings.padStart("test", 10, ' ');//填充空格什么的

        Strings.padEnd("test", 10, ' ');//填充空格什么的

        Strings.repeat("", 10);/* 业务中目前还没发现用途 */

    }

    public static void testJoiner() {
        /**
         * 项目中使用合并id什么的，很多用的直接是append，这种方式我觉得
         * 挺啰嗦，不够优雅。建议使用joiner
         * joniner有若干join重载，建议看简单看一下
         * appendTo也不说了，很简单，就是追加到Builder后面而已
         */
        System.out.println(Joiner.on(',').skipNulls().join("a", null, "dd", "dd"));//a,b,null

        System.out.println(Joiner.on(",").useForNull("default").join(Arrays.asList(1, 5, 7), null));

        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        System.out.println(Joiner.on(",").withKeyValueSeparator("=>").join(map));//a=>b,c=>d

    }

    public static void testSplit() {
        /**
         *  why splitter，
         *  https://code.google.com/p/eventbus-libraries/wiki/StringsExplained#Splitter
         *  重点就是我们能控制分割行为 目前项目中的自由打包，
         *  只有龙爷用过一次Splitter类其他都是别的代码中用的，
         *  也不算多。我们项目中用过最多的还是commons的
         *  StringUtil以及jdk的String类自带的split方法。
         *  因此有必要介绍以下怎么用这个类。其实很简单，只要
         *  看一眼如下代码就都能明白了：
         */
        Splitter.on(',');
        Splitter.on(",");
        Splitter.on(CharMatcher.WHITESPACE);
        Splitter.onPattern("[^a-zA-Z]");
        Splitter.fixedLength(3);/** 固定长度分割 */

        /* 分割行为 */
        //returns "a", "c", "d"
        Splitter.on(',').omitEmptyStrings().split("a,,c,d");//a c d
        //returns "a", "b", "c", "d"
        Splitter.on(',').trimResults().split("a, b, c, d");
        //returns "a ", "b_ ", "c".
        Splitter.on(',').trimResults(CharMatcher.is('_')).split("_a ,_b_ ,c__");
        // returns "a", "b", "c,d"
        Splitter.on(',').limit(3).split("a,b,c,d");
        /**
         *  我的理解是使用guava的这个东西可以更好的控制行为，一旦我们的
         *  匹配字符串的匹配规则发生了变化，我们更容易重构自己的代码，但
         *  是对于内存来说是有一定的开销的。请看源代码。
         */
        /**除此之外*/
        Map<String, String> map = Splitter.on(';')
          .trimResults()
          .withKeyValueSeparator("=>")
          .split("a=>b ; c=>b");
        /** 可以用来生成一个map，这可能开发我们的想象力，比如配置库配置文件处理 */
    }

    public static void main(String[] args) {
        testJoiner();
    }
}