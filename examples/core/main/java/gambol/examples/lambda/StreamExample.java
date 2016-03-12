package gambol.examples.lambda;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by zhenbao.zhou on 16/1/18.
 */
public class StreamExample {

    public static void main(String[] args) {
        List<Object> l = Lists.newArrayList(1, null, 3, 4, null, "4");
        long re = l.stream().filter(m -> m != null).map(k -> {
            if (k instanceof String)
                return Integer.parseInt((String) k);
            else
                return k;
        }).map(k -> (Integer) k + 2).count();

        System.out.println("re:" + re);

        re = l.stream().filter(m -> m != null).map(k -> {
            if (k instanceof String)
                return Integer.parseInt((String) k);
            else
                return k;
        }).mapToInt(k -> (Integer) k + 2).distinct().peek(System.out::println).skip(1).sum();

        System.out.print("re:" + re);



        Stream<List<Integer>> inputStream = Stream.of( Arrays.asList(2, 3), Arrays.asList(4, 5, 6), new ArrayList<Integer>());

        // 6, 7, 8
        Stream<Integer> str = inputStream.flatMap(s -> s.stream().map(k -> k + 4)).limit(3).peek(System.out::println);

        System.out.println(" kkk " + str.reduce((sum, item)-> sum + 2 * item).get());
    }
}
