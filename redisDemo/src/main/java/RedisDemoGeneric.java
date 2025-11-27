import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Set;

public class RedisDemoGeneric {
    public static void test(Jedis jedis) {
        jedis.flushAll();
        jedis.set("key","111");
        String value = jedis.get("key");
        System.out.println(value);

    }
    public static void test2(Jedis jedis)  {
        jedis.flushAll();
        SetParams params = new SetParams();
        params.ex(10);
        jedis.set("key","111",params);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long time = jedis.ttl("key");
        System.out.println(time);
    }
    public static void test3(Jedis jedis) {
        jedis.flushAll();
        jedis.set("key","111");
        boolean result = jedis.exists("key");
        System.out.println(result);
        jedis.del("key");
        result = jedis.exists("key");
        System.out.println(result);
    }
    public static void test4(Jedis jedis) {
        jedis.flushAll();
        jedis.set("key","111");
        jedis.set("key2","222");
        jedis.set("key3","333");
        Set<String> set = jedis.keys("*");
        System.out.println(set);


    }


    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("tcp://127.0.0.1:8888");
        try (Jedis jedis = jedisPool.getResource()) {
//            String pong = jedis.ping();
//            System.out.println(pong);
            //test(jedis);
            //test2(jedis);
            //test3(jedis);
            test4(jedis);
        }
    }
}
