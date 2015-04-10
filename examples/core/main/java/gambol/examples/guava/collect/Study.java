package gambol.examples.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author kris.zhang
 */
public class Study {
    public static void main(String[] args) {
        /**
         * guava 提供了jdk的集合类所没有的扩展，相当多的新类，与common的集合类有些类似 这是guava最成熟和最为人所知的部分
         * 在业务代码中也存在相当多的用途：
         * （1）不可变集合
         * （2）新集合类，基本我们用不太多
         * （3）集合工具类
         * （4）扩展工具类，迭代器
         */

    }

    public void immutable() {
        /**
         * 为什么要使用不可变集合
         * 当对象被不可信的库调用时，不可变形式是安全的
         * 不可变对象被多个线程调用时，不存在竞态条件问题
         * 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
         * 不可变对象因为有固定不变，可以作为常量来安全使用。
         *
         * JDK也提供了Collections.unmodifiableXXX方法把集合包装为不可变形式，但我们认为不够好：
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
         * 具体的使用我就不讲了，大部分我都没有用过，只讲一下他们是什么东西就足够了，
         * 大家实践中有需要可以快速定位到具体的集合类就OK了
         * */

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

    public void util() {
        /** (1)可以避免菱形表达式 (2)初始化时方便指定元素 */
        Lists.newArrayList();
        Sets.newConcurrentHashSet();
        Maps.newHashMap();
        Queues.newArrayDeque();

        /** 常用的创建同步集合类 */
        Maps.newConcurrentMap();
        Lists.newCopyOnWriteArrayList();
        Sets.newConcurrentHashSet();
        Sets.newCopyOnWriteArraySet();

        /** 阻塞队列和阻塞双端队列 */
        Queues.newArrayBlockingQueue(1);
        Queues.newLinkedBlockingQueue();
        Queues.newConcurrentLinkedQueue();
        Queues.newLinkedBlockingDeque();
        Queues.newPriorityBlockingQueue();
        Queues.newSynchronousQueue();

        /** 迭代器常用 */
        Iterables.concat(Ints.asList(1, 2, 3, 4), Lists.newCopyOnWriteArrayList());
        Iterables.frequency(Lists.newArrayList(), "我出现的次数");
        Iterable<List<String>> i = Iterables.partition(Lists.<String>newArrayList(), 12);//划分为12份儿
        Iterables.filter(Lists.newArrayList("","",""), Predicates.containsPattern("nihao"));
        //.....

        /** 集合操作 */
        Sets.intersection(Sets.newHashSet(), Sets.newHashSet());/** 交集 */
        Sets.union(Sets.newHashSet(), Sets.newHashSet());/** 并集 */
        Sets.difference(Sets.newHashSet(), Sets.newHashSet());/** 差集 */
        Sets.powerSet(Sets.newHashSet());/**返回所有子集*/
        Sets.cartesianProduct(Sets.newHashSet(), Sets.newHashSet());/** 返回笛卡尔积 */

        /** 集合过滤器 */
        Collection<Object> newCollection = Collections2.filter(Lists.newArrayList(), new Predicate<Object>() {
            @Override
            public boolean apply(Object input) {
                if (input == null) {
                    return false;
                }
                return true;
            }
        });

        Maps.filterEntries(ImmutableMap.of("a", 1, "b", 2), Predicates.alwaysFalse());
        Maps.filterKeys(ImmutableMap.of("a", 1, "b", 2), Predicates.containsPattern("[a-zA-Z]*"));
        Maps.filterValues(ImmutableMap.of("a", 1, "b", 2), Predicates.equalTo(2));

        Sets.filter(ImmutableSet.of(1, 2, 3), Predicates.notNull());

        /** 集合转换器 */
        newCollection = Collections2.transform(Lists.newArrayList(), new Function<Object, Object>() {
            @Nullable
            @Override public Object apply(Object input) {
                return input.hashCode();
            }
        });

        Maps.transformValues(ImmutableMap.of("a", 1, "b", 2), Functions.toStringFunction());
        Maps.transformEntries(ImmutableMap.of("a", 1, "b", 2),new Maps.EntryTransformer<String,Integer,String>() {
            @Override
            public String transformEntry(@Nullable String key, @Nullable Integer value) {
                return value.toString();
            }
        });

        Lists.transform(ImmutableList.of(1,2,3), Functions.toStringFunction());

    }

    public void extent() {
        //这里不说了，不常用，我们基本用不到可能
        /**
         * Forwarding装饰器 用于静态代理集合类。
         * 除此之外，guava提供了相当丰富的代理接口的抽象类，可以点ForwardingList进去看看
         **/
        class AddLoggingList<E> extends ForwardingList<E> {
            List<E> delegate; // backing list

            @Override protected List<E> delegate() {
                return delegate;
            }

            @Override public void add(int index, E elem) {
                //log(index, elem);
                super.add(index, elem);
            }

            @Override public boolean add(E elem) {
                return standardAdd(elem); // 用add(int, E)实现
            }

            @Override public boolean addAll(Collection<? extends E> c) {
                return standardAddAll(c); // 用add实现
            }
        }

        /** PeekingIterator */
        PeekingIterator<String> iter = Iterators.peekingIterator(Lists.<String>newArrayList().iterator());
        while (iter.hasNext()) {
            iter.peek();//不是next，事先窥探
            iter.next();//移动到下一个
        }

        /** 迭代器静态代理，相当有用 */
        //加入说我们得到了Iterator<String>，但是我们要返回其中不为null的迭代器
        skipNulls(null);

        Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) { // 注意初始值1!
            protected Integer computeNext(Integer previous) {
                return (previous == 1 << 30) ? null : previous * 2;
            }
        };

        /** http://ifeve.com/google-guava-collectionhelpersexplained/ */
    }

    /** 非常简洁的封装，不必在遍历一遍迭代其了 */
    public static Iterator<String> skipNulls(final Iterator<String> in) {
        //普通的实现方式
        new Iterator<String>() {
            @Override public boolean hasNext() {
                return in.hasNext();
            }

            @Override public String next() {
                //循环in,找到非null值返回。。。。
                return null;
            }

            @Override public void remove() {
                in.remove();
            }
        };

        //guava实现方式
        return new AbstractIterator<String>() {

            @Override protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (s != null) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
    }

}
