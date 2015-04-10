package gambol.examples.guava.base.order;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author kris.zhang
 */
public class Study {

    public static class Bean {
        @Getter @Setter String name;
        @Getter @Setter int age;
    }

    public static void main(String[] args) {

        List<String> list1 = ImmutableList.of("b","c","a");
        List<String> list2 = Lists.newArrayList("3","2","1");

        /** 按照什么去排序？ */
        System.out.println(Ordering.natural().immutableSortedCopy(list1));//自然序
        System.out.println(Ordering.natural().sortedCopy(list1));//自然序
        System.out.println(Ordering.usingToString().immutableSortedCopy(list1));//字符序
        System.out.println(Ordering.allEqual().immutableSortedCopy(list1));//所有都是相等，null不是
        System.out.println(Ordering.from( //自己注入比较器
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
        ));

        /** 如果为0那么调用下一次排序 */
        Ordering.compound(
                ImmutableList.<Comparator<Bean>>builder().add(
                        new Comparator<Bean>() {
                            @Override
                            public int compare(Bean o1, Bean o2) {
                                return (o1.age < o2.age) ? -1 : ((o2.age == o1.age) ? 0 : 1);
                            }
                        }
                ).add(
                        new Comparator<Bean>() {
                            @Override
                            public int compare(Bean o1, Bean o2) {
                                return o1.name.compareTo(o2.name);
                            }
                        }
                ).build()
        );

        System.out.println(Ordering.arbitrary().sortedCopy(list1)); //根据hashcode比较

        Ordering.natural()
                .nullsFirst() //null在最前
                .nullsLast()  //null最后
                .reverse(); //反转

        /** 其他方法 */
        Ordering.natural().binarySearch(list1,"a");
        Ordering.natural().compare("a","b");
        Ordering.natural().greatestOf(list1,2);//第二大元素
        Ordering.natural().leastOf(list1,2);//第二小元素
        Ordering.natural().isOrdered(list1);//是否已经有序
        Ordering.natural().min(list1);//最小的
        Ordering.natural().max(list1);//最大的

        /** 对结果进行一定的处理，然后根据结果在去排序 */
        Ordering.natural().onResultOf(new Function<String, String>() {
            @Nullable @Override
            public String apply(String input) {
                if (input == null) {
                    return "";
                }
                return input;
            }
        });

        /**
         * 为啥龙强这里注释掉了
         * if(soList != null){
         *   // soList = Ordering.natural().sortedCopy(soList);
         *      Collections.sort(soList);
         *  }
         */

        //会改变原有数组reference
        Collections.sort(list2);
        System.out.println(list2);

    }
}
