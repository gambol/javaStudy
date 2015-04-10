package gambol.examples.guava.primitives;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.*;

/**
 * @author kris.zhang
 */
public class Study {
    public static void main(String[] args) {
        /** unsigned 支持 就是封装了int对象，保证他的无符号性 */
        UnsignedInteger  unsignedInt = UnsignedInteger.valueOf(3);
        UnsignedLong unsignedLong = UnsignedLong.valueOf(12);
        unsignedInt.plus(UnsignedInteger.valueOf(12));
        unsignedInt.minus(UnsignedInteger.valueOf(12));
        unsignedInt.times(UnsignedInteger.valueOf(12));
        unsignedInt.dividedBy(UnsignedInteger.valueOf(12));
        unsignedInt.mod(UnsignedInteger.valueOf(12));
        unsignedInt.longValue();
        unsignedInt.intValue();
        unsignedInt.floatValue();

        /** 提供元数据数组的支持 */
        Ints.asList(1, 2, 3, 4);//{1,2,3,4}
        Longs.asList(1,2,3,4);
        Floats.asList(1f,2f,3f,4f);
        Bytes.asList((byte)1,(byte)2,(byte)3,(byte)4);

        Lists.asList(1,2,new Integer[] {1,2,3});
        Lists.newArrayList(1,2,3,4);
        ImmutableList.of(1,2,3);
        Ints.asList(1, 2, 3, 4);//大小不可变

        Ints.max(1, 2, 3, 4);//4
        Ints.min(1, 2, 3, 4);//1
        Longs.join(":", 1, 2, 3, 4);//拼接1:2:3:4
        Floats.tryParse("2.3");//注意try的命名！表示不抛出异常，我们就不要再写什么parseWithoutException，太长了。
        Bytes.concat(new byte[] { 1, 2 }, new byte[] { 3, 4 });//拼接数组成 {1,2,3,4}

    }
}
