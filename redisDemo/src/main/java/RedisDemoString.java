import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;


public class RedisDemoString {
    public static void test(Jedis jedis) {
        jedis.flushAll();
        jedis.mset("key","1","key2","2");
        List<String> values = jedis.mget("key","key2");
        System.out.println(values);

    }
    public static void test2(Jedis jedis) {
        jedis.flushAll();
        jedis.set("key","asdf");
        String s = jedis.getrange("key",1,2);
        System.out.println(s);
        jedis.setrange("key",1,"as");
        s = jedis.get("key");
        System.out.println(s);
    }
    public static void test3(Jedis jedis) {
        jedis.flushAll();
        jedis.set("key","asd");
        jedis.append("key","qwe");
        String s = jedis.get("key");
        System.out.println(s);
        jedis.set("key2","1");
        jedis.incr("key2");
        System.out.println(jedis.get("key2"));
    }
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("tcp://127.0.0.1:8888");
        try(Jedis jedis = jedisPool.getResource()) {
            //test(jedis);
            //test2(jedis);
            test3(jedis);
        }
    }
}
