package gambol.examples.guava.hash;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.*;
import com.google.common.io.BaseEncoding;

import java.util.List;

/**
 * 包含hash的使用和bloom过滤器
 *
 * （1）常用hash算法
 * （2）一致性hash算法
 * （3）bloom过滤器
 *
 * @author kris.zhang
 */
public class Study {

    static class Person {
        String name;
    }

    public static void hasing() {
        /**
         *    guava的hash对象模型
         *    PrimitiveSink -> Hasher 用于序列化原始对象，像漏斗一样
         *    HashFunction -> hash算法的具体实现
         *    最后的结果将会生成HashCode对象，他会根据你的需要生成相应的hash值，比如long或者bytes
         * */
        HashFunction hf =
            Hashing.md5();
            Hashing.sha1();
            Hashing.sha512();
            Hashing.sha256();
            Hashing.adler32();
            Hashing.crc32();

        HashCode hc = hf.newHasher()
                .putLong(123)
                .putString("name", Charsets.UTF_8) //说道Charset这里有一个StandardCharsets.UTF_8;
                .putObject(new Person(), new Funnel<Person>() {
                    @Override
                    public void funnel(Person from, PrimitiveSink into) {
                        //这里能够将对象from 装入 容器PrimitiveSink into，从而将其
                        //进行序列化
                        into.putUnencodedChars(from.name);
                    }
                })
                .putObject(new String("haha"),Funnels.unencodedCharsFunnel())
                .hash(); // 调用hash进行hash
        System.out.println(hc.bits());
        System.out.println(hc.asLong());
        System.out.println(hc.asInt());
        System.out.println(BaseEncoding.base64().encode(hc.asBytes()));

        Hashing.md5().newHasher().putString("haha",Charsets.UTF_8).hash().asLong();//stream style
        // MD5Util.md5("haha");//当然，你使用通用工具，可能更简便，不过失去了灵活性

        /** 项目中我用在cps中的md5 */
    }

    //支持一致性hash http://blog.csdn.net/cywosp/article/details/23397179
    public static void consistentHash() {
        System.out.println(Hashing.consistentHash(1, 12));
        Hashing.consistentHash(null/** HashCode */,12);
    }

    /** 普通的用法 */
    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            System.out.println(Hashing.consistentHash(i, 12));
        }
        System.out.println("现在变为11台机器了");
        for (int i = 0; i < 11; i++) {
            System.out.println(Hashing.consistentHash(i, 11));
        }
    }

    /** bloom过滤器使用 */
    public static void bloomFilter() {
        //guava也支持bloom过滤器
        BloomFilter friends = BloomFilter.create(new Funnel<Person>() {
            @Override
            public void funnel(Person from, PrimitiveSink into) {
                into.putUnencodedChars(from.name);
            }
        }, 500, 0.01);

        Person dude = new Person();
        List<Person> friendsList = Lists.newArrayList(
                new Person()
        );

        for(Person friend : friendsList) {
            friends.put(friend);
        }

        // much later
        if (friends.mightContain(dude)) {
            // the probability that dude reached this place if he isn't a friend is 1%
            // we might, for example, start asynchronously loading things for dude while we do a more expensive exact check
            System.out.println("exists");
        }
    }
}
