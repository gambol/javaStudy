package gambol.examples.writecode.prettycode;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class IfKiller3 {

    /**
     * 正常业务代码
     * 
     * @param input
     * @return
     */
    public String foo1(int input) {

        if (input <= 3 && input >= 0) {
            return "A";
        }

        if (input == 4) {
            return "B";
        }

        if (input >= 5) {
            return "C";
        }

        return "D";
    }

    Map<Predicate, String> map = ImmutableMap.of(new SmallOnePredicate(), "A", new MiddleOnePredicate(), "B",
            new LargeOnePredicate(), "C");

    final static String DEFAULT = "D";

    String foo2(int input) {

        for (Predicate p : map.keySet()) {
            if (p.apply(input)) {
                return map.get(p);
            }
        }

        return DEFAULT;
    }

    static class SmallOnePredicate implements Predicate<Integer> {
        @Override
        public boolean apply(Integer input) {
            return (input <= 3 && input >= 0);
        }
    }

    static class MiddleOnePredicate implements Predicate<Integer> {
        @Override
        public boolean apply(Integer input) {
            return input == 4;
        }
    }

    static class LargeOnePredicate implements Predicate<Integer> {
        @Override
        public boolean apply(Integer input) {
            return input >= 5;
        }
    }
}
