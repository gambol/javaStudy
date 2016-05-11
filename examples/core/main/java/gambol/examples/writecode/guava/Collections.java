package gambol.examples.writecode.guava;

import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Queues;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 演示guava里的各种集合类 Created by zhenbao.zhou on 16/5/10.
 */
public class Collections {

    public void util() {
        /**
         * 避免菱形表达式
         */
        List list = Lists.newArrayList();
        Set set = Sets.newHashSet();
        Map map = Maps.newHashMap();
        Queue queue = Queues.newArrayDeque();

        /**
         * 创建常见的同步集合
         */
        list = Lists.newCopyOnWriteArrayList();
        map = Maps.newConcurrentMap();
        set = Sets.newCopyOnWriteArraySet();
        Queues.newArrayBlockingQueue(5);

        /**
         * 指定初始值
         */
        Lists.newArrayList(1, 2, 3);
        Sets.newHashSet(1, 2, 1, 3);

        // 迭代器
        Iterable it = Iterables.concat(Ints.asList(1, 2, 3, 4), Lists.newCopyOnWriteArrayList());
        Iterator i = Iterables.concat(it, Doubles.asList(1.0, 2.0)).iterator();
        while (i.hasNext()) {
            System.out.println("it:" + i.next().toString());
        }

        /** 集合过滤器 */
        Collection<Integer> newCollection = Collections2.filter(Lists.<Integer> newArrayList(4, 5, 6, 7),
                new Predicate<Integer>() {
                    @Override
                    public boolean apply(Integer input) {
                        return input % 2 == 0;
                    }
                });
        i = newCollection.iterator();
        while (i.hasNext()) {
            System.out.println("new collection:" + i.next());
        }

        Lists.transform(ImmutableList.of(1, 2, 3), Functions.toStringFunction());
    }


    public void immutable() {
        /**
         * 为什么要使用不可变集合
         * 当对象被不可信的库调用时，不可变形式是安全的
         * 不可变对象被多个线程调用时，不存在竞态条件问题
         * 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
         * 不可变对象因为有固定不变，可以作为常量来安全使用。
         *
         * JDK也提供了Collections.unmodifiableXXX方法把集合包装为不可变形式，但认为不够好：
         * 笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景；
         * 不安全：要保证没人通过原集合的引用进行修改，返回的集合才是事实上不可变的；
         * 低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等。
         *
         * 注意：如果你需要在不可变集合中使用null，请使用JDK中的Collections.unmodifiableXXX方法
         */
        /** 有几个常用的 */
        /** 变化时，抛出：UnsupportedOperationException */
        Set<String> set = ImmutableSet.of("1", "2", "3");
        set = ImmutableSet.<String>builder()
                .add("1")
                .add("2")
                .add("3")
                .build();

        List<String> list = ImmutableList.of("1", "2", "3");
        list = ImmutableList.<String>builder()
                .add("1")
                .add("2")
                .add("3")
                .build();

        Map<Integer, String> map = ImmutableMap.of(1, "1", 2, "2", 3, "3");
        map = ImmutableMap.<Integer, String>builder()
                .put(1, "1")
                .put(2, "2")
                .put(3, "3")
                .build();

        //不要用：(1)非immutable （2）perm空间
        List<String> no = new ArrayList<String>() {
            {
                add("");
                add("");
                add("");
                add("");
            }
        };

        /**
         * ImmutableTable
         * ImmutableClassToInstanceMap
         * ImmutableBiMap
         * ImmutableSetMultimap
         * ImmutableListMultimap
         * ImmutableMultimap
         * ImmutableSortedMap
         *
         * ImmutableSortedMultiset
         * ImmutableMultiset
         * ImmutableSortedSet
         * 上面的Immutable都有对应的of方法和builder共你使用
         *
         * */

        /* 注意：如果你需要在不可变集合中使用null，请使用JDK中的Collections.unmodifiableXXX方法*/
    }

    public void newCollection() {

        /**
         * Multiset：
         *      Multiset {a, a, b}和{a, b, a}是相等的这于tuple是不同的
         * 使用：比如统一文档中单词出现的次数
         * 各种实现：
         * HashMultiset           对应HashSet
         * TreeMultiset           对应TreeSet
         * LinkedHashMultiset     对应LinkedHashSet
         * ConcurrentHashMultiset //同步的
         * ImmutableMultiset      //不可变化的
         */
        Multiset<String> mset = HashMultiset.create(32);
        mset.add("hello");
        mset.add("hello");
        mset.add("hello");
        System.out.println(mset.count("hello"));//3
        System.out.println(mset.size());//1，3
        for (String e : mset.elementSet()) {
            System.out.println(e);
        }
        mset.add("hello", 10);
        mset.remove("hello");

        /**
         * Multimap
         *      用来代替 Map<K, List<V>>或Map<K, Set<V>>
         *
         * 各种实现：
         * ListMultimap 接口
         * SetMultimap  接口
         *
         * ArrayListMultimap
         * TreeMultimap
         * LinkedListMultimap
         * LinkedHashMultimap
         * HashMultimap
         * TreeMultimap
         *
         * ImmutableListMultimap
         * ImmutableSetMultimap
         * */
        SetMultimap<String, String> setMultimap = HashMultimap.create();
        SortedSetMultimap<String, String> sortedSetMultimap = TreeMultimap.create();
        ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
        listMultimap.put("key", "value0");
        listMultimap.put("key", "value1");
        listMultimap.put("key", "value2");
        listMultimap.get("key");//{value0,value1,value2}
        listMultimap.putAll("key",Lists.asList("value0",new String[] {""}));

        /**
         * BiMap
         *      键值对的双向映射，必须保证一一映射
         * 各种实现：
         * HashBiMap
         * ImmutableBiMap
         * EnumBiMap
         * EnumHashBiMap
         *
         */
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("key", "value");
        biMap.inverse().get("value");//we got key

        /**
         * Table
         *  提供行列表的数据结构如：
         *     0   1
         *   |-------|
         * 0 | 1 | 2 |
         *   |-------|
         * 1 | 3 | 4 |
         *   |-------|
         *
         *  各种实现：
         *  HashBasedTable
         *  TreeBasedTable
         *  ImmutableTable
         *
         *  ArrayTable
         */
        Table<Integer, Integer, String> table = HashBasedTable.create();
        table.put(0, 0, "i1");
        table.put(0, 1, "i2");
        table.put(1, 0, "i3");
        table.put(1, 1, "i4");
        table.get(0, 1);//i2
        table.row(0);//i1 i2
        table.column(0);//i1 i3

        /**
         * ClassToInstanceMap
         * 它的键是类型，而值是符合键所指类型的对象
         *
         * 实现：
         * MutableClassToInstanceMap
         * ImmutableClassToInstanceMap
         */
        ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
        numberDefaults.putInstance(Double.class, Double.valueOf(0));
        numberDefaults.getInstance(Integer.class);

        /**
         * RangeSet和RangeMap
         * ImmutableRangeSet
         * TreeRangeSet
         * TreeRangeMap
         * ImmutableRangeMap
         * */
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}

        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}

        /** 可能用在合并日期，价格等方面 */
    }

    public static void main(String[] args) {
        new Collections().util();
    }

}
