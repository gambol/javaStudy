package org.gambol.modules.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenbao.zhou on 17/7/4.
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("127.0.0.1");
    }
    /**
     * 简单添加
     */
    @Test
    public void test1() {
        String name = "name";
        String value = "qq";
        jedis.set(name, value);
        System.out.println("追加前：" + jedis.get(name)); // 追加前：qq
        // 在原有值得基础上添加,如若之前没有该key，则导入该key
        jedis.append(name, "ww");
        System.out.println("追加后：" + jedis.get(name)); // 追加后：qqww

        jedis.append("id", "ee");
        System.out.println("没此key：" + jedis.get(name));
        System.out.println("get此key：" + jedis.get("id"));
    }
    /**
     * mset 是设置多个key-value值 参数（key1,value1,key2,value2,...,keyn,valuen） mget
     * 是获取多个key所对应的value值 参数（key1,key2,key3,...,keyn） 返回的是个list
     */
    @Test
    public void test2() {
        jedis.mset("name4", "aa", "name4", "bb", "name5", "cc");
        System.out.println(jedis.mget("name4", "name6", "name5", "name00"));
    }
    /**
     * map
     */
    @Test
    public void test3() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "fujianchao");
        map.put("password", "123");
        map.put("age", "12");
        // 存入一个map
        jedis.hmset("user", map);
        // map key的个数
        System.out.println("map的key的个数" + jedis.hlen("user"));
        // map key
        System.out.println("map的key" + jedis.hkeys("user"));
        // map value
        System.out.println("map的value" + jedis.hvals("user"));
        // (String key, String... fields)返回值是一个list
        List<String> list = jedis.hmget("user", "age", "name");
        System.out.println("redis中key的各个 fields值："
                + jedis.hmget("user", "age", "name") + list.size());
        // 删除map中的某一个键 的值 password
        // 当然 (key, fields) 也可以是多个fields
        jedis.hdel("user", "age");
        System.out.println("删除后map的key" + jedis.hkeys("user"));
    }
    /**
     * list
     */
    @Test
    public void test4() {
        jedis.lpush("list", "aa");
        jedis.lpush("list", "bb");
        jedis.lpush("list", "cc");
        System.out.println(jedis.lrange("list", 0, -1));
        System.out.println(jedis.lrange("list", 0, 1));
        System.out.println(jedis.lpop("list")); // 栈顶
        jedis.del("list");
    }
}
