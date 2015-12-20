package gambol.examples.lambda.template;

import com.google.common.collect.Lists;
import jdk.nashorn.internal.codegen.CompilerConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by zhenbao.zhou on 15/10/29.
 */
@Slf4j
public class FooTemplate<T> {


    public <T> void foo(T t, FooCallBack callBack) {
        log.info("execute begin");
        hello(t);
        callBack.execute(t);
        log.info("execute end");
    }

    private <T> void hello(T t) {
        log.info("Hello. input is:{}", t);
    }

    public int cal(int x, int y, Calculator calculator) {
        return calculator.calc(x, y);
    }


    public void a () {
        Function<String, String> func = s -> s.substring(4);
        func = String::toString;

      //  Systemfunc.apply("444");

    }

    interface Calculator {
        int calc(int x, int y);
    }

    public static void main(String[] args) {
        FooTemplate fooTemplate = new FooTemplate();
//
//        new FooTemplate<String>().foo("gambol", new FooCallBack<String>() {
//            @Override
//            public void execute(String s) {
//                log.info(" in new FooCallback. wakaka. {}", s);
//            }
//        });
//
//        new FooTemplate<String>().foo("gambol", s -> log.info(" lambda wakaka:{}", s));
//
//        FooCallBack c = s -> log.info("sss:{}", s);
//
//        FooCallBack<String> d = (String x) -> {
//            log.info("xxxx, ddd, {}", x);
//            x.substring(1);
//        };

//        fooTemplate.foo("kk", d);

        int k = fooTemplate.cal(4, 5, (x, y) -> {
                    log.info("hello calcor");
                    return x + y;
                }
        );

        log.info("k is:{}", k);

        k = fooTemplate.cal(4, 5, (int x, int y) -> 40);
        log.info("k is:{}", k);

        Comparator<Integer> c = (x, y) -> x + 10 - y;

        new Thread(()->{

        }).start();

        Callable callable = () -> 1;

        Supplier supplier = () -> 4;

        Callable call = 4  > 3 ? () -> 1 : () -> 2;

        Comparator<Integer> comparator = Comparator.comparing(x -> x);

        Comparator<Student> comparator2 = Comparator.comparing(s -> s.getScore());

        comparator2 = Comparator.comparing(Student::getScore);


        Function<String, String> func = s -> s.substring(4);
        func = String::toString;

        log.info("hahah: {} ", func.apply("444"));

        BiPredicate<Integer, Integer> predicate = (x, y) -> x > y;

        BiPredicate<String, String> p = String::startsWith;

        log.info(" predict:{}", p.test("aaa, bb", "aa"));

        List<Student> studentList = Lists.newArrayList();

        Collections.sort(studentList, Comparator.comparing(s -> s.getScore()));
        Collections.sort(studentList, Comparator.comparing(Student::getScore));

        studentList.sort(Comparator.comparing(Student::getScore));

    }


    static class Student {
        int score;
        int age;

        public int getScore() {
            return score;
        }

        public int getAge() {
            return age;
        }
    }
}
