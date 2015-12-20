package gambol.examples.lambda;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * User: zhenbao.zhou
 * Date: 2/27/15
 * Time: 5:52 PM
 */
public class LambdaExample {

    void foo() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Virat", "Kohli"));
        personList.add(new Person("Arun", "Kumar"));
        personList.add(new Person("Rajesh", "Mohan"));
        personList.add(new Person("Rahul", "Dravid"));

        // 在JDK8之前，使用匿名函数解决这个问题
        Collections.sort(personList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                return p1.firstName.compareTo(p2.firstName);
            }
        });

        // JDK8 之后，可以用lambda表达式
        Collections.sort(personList, (Person p1, Person p2) -> p1.firstName.compareTo(p2.firstName));

        /**
         * 新增的Function函数， Person是输入，String是输出
         */
        Function<Person, String> wacb = a -> {
            return "kkdd" + "cc";
        };

        // 也可以不用"{}" 包含起来. 最后一行的结果，默认作为返回值
        Function<Person, String> waca = a -> "kkdd" + "cc";

        // 遍历
        for (Person person: personList) {
           // System.out.println(person);
            System.out.println(person.printCustom(waca));
        }

        /**
         * 可以遍历
         */
        personList.forEach(p->System.out.println(p.toString() + " kkkkaaa"));

        /**
         * Predicate用来进行集合过滤
         */
        Predicate<Person> startsWithAPerson = p->p.firstName.startsWith("A");
        personList.forEach(p-> {
            if (startsWithAPerson.test(p)) {
                System.out.println("starts with A:" + p);
            }
        });

        /**
         * 如果只有一个方法，可以加上默认函数编程
         */
        Runnable runner = () -> {
            System.out.println("I am lambda runner.");
        };
        runner.run();

        MyInterfaceA myInterface = (String s) -> {
            System.out.println(" I am in myInterface" + s);
        };

        MyInterfaceB myInterfaceB = () -> {
        };

        myInterface.a("kk");

        List<Person> ps = new ArrayList<>();
        Random r = new Random();
        for (int i=0; i<10;i++){
            ps.add(new Person("firstname"+i, "lastname"+i, r.nextInt(30) + 1));
        }
        // 过滤条件>18岁
        Predicate<Person> adults = p -> p.getAge() > 18;

        //最小年龄
        OptionalInt minAge = ps.parallelStream().filter(adults)
          .mapToInt(p -> p.getAge()).min();
        System.out.println("最小年龄：" + minAge.getAsInt());

        // 求18岁以上的平均值
        OptionalDouble average =
          ps.parallelStream().filter(adults)
            .mapToDouble(p -> {
                return p.getAge();
            }).average();

        System.out.println("平均年龄：" + average.getAsDouble());

    }

    public static void main(String[] args) {
        new LambdaExample().foo();;
    }

    class Person {
        String firstName;
        String secondName;
        int age;

        Person(String f, String s) {
            this.firstName = f;
            this.secondName = s;
        }

        Person(String f, String s, int age) {
            this.firstName = f;
            this.secondName = s;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
              "firstName='" + firstName + '\'' +
              ", secondName='" + secondName + '\'' +
              '}';
        }

        public int getAge() {
            return age;
        }

        public String printCustom(Function<Person, String> f) {
            return f.apply(this);
        }
    }

    // 理论上来说，当这个接口只有一个函数时，才可以用lambda函数
    interface MyInterfaceA {
        void a(String a);
    }

    // 犹豫 bb 是默认函数
    interface MyInterfaceB{
        void b();

        // default关键字，给接口带了默认实现
        default void bb(String s) {
        }

        // 这种Object里默认的函数，也不会影响是否可以使用lambda函数
        boolean equals(Object obj);
        String toString();
    }

    class ClassB implements  MyInterfaceB {
        @Override
        public void b() {  }

        public void bb() {  }
    }

}
