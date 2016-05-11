package gambol.examples.writecode.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Map;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class StringExample {
    public void testStrings() {
        Strings.isNullOrEmpty(null); //null return ok

        /**
         * 经常使用. 减少外层对于null的判断
         */
        String emptyString = Strings.nullToEmpty(null);

          /* 这个可以用来代替如下代码 */
        StringBuilder b = new StringBuilder();
        b.append("xx").append("xx");
        Strings.padStart("test", 10, ' ');//填充空格
    }

    public void testJoiner() {
        System.out.println(Joiner.on(',').skipNulls().join("a", null, "dd", "dd"));//a,b,null

        System.out.println(Joiner.on(",").useForNull("default").join(Lists.newArrayList(1, 5, 7), null));

    }

    public void testSplitter() {

        // returns "1", "2", "3", "4"
        Splitter.on(",").trimResults().split("1,2,   3,4   ");

        // returns a map  "a"=>"b" "c"=>"b"
        Map<String, String> map = Splitter.on(';')
                .trimResults()
                .withKeyValueSeparator("=>")
                .split("a=>b ; c=>b");
    }

}
