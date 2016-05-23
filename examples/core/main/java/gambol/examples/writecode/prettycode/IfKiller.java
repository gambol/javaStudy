package gambol.examples.writecode.prettycode;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class IfKiller {

    /**
     * 正常业务代码
     * @param input
     * @return
     */
    public String foo1(int input) {

        if (input == 3) {
            return "A";
        }

        if (input == 4) {
            return "B";
        }

        if (input == 5) {
            return "C";
        }

        return "D";
    }


    public String foo2(int input) {
        Map<Integer, String> valueMap = ImmutableMap.of(3, "A", 4, "B", 5, "C");

        return valueMap.getOrDefault(input, "D");
    }
}
