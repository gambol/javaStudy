package org.gambol.modules.redis;

import gambol.examples.redis.JedisTemplate;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zhenbao.zhou on 16/1/11.
 */
public class JedisTemplateTest extends TestCase {

    @Test
    public void testRedisConnection() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.86.43.225"); // 连接到 l-gqta4.h.dev.cn0
        JedisTemplate template = new JedisTemplate(pool);
        String k = String.valueOf(System.nanoTime());
        String value = "value";
        template.set(k, value);
        String v = template.get(k);
        Assert.assertEquals(v, value);
    }
}
