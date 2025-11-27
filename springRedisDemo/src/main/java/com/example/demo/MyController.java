package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MyController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/testString")
    public String testString() {
        redisTemplate.opsForValue().set("key","111");
        String s = redisTemplate.opsForValue().get("key");
        System.out.println(s);
        return "ok";
    }

    @GetMapping("/testList")
    public String testList() {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.flushAll();
            return null;
        });
        redisTemplate.opsForList().leftPush("key","111");
        redisTemplate.opsForList().leftPush("key","222");
        List<String> values = redisTemplate.opsForList().range("key", 0, -1);
        System.out.println(values);
        return "ok";
    }

    @GetMapping("/testSet")
    public String testSet() {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.flushAll();
            return null;
        });
        redisTemplate.opsForSet().add("key","111","222","333");
        Set<String> members = redisTemplate.opsForSet().members("key");
        System.out.println(members);
        redisTemplate.opsForSet().remove("key","111");
        Boolean key = redisTemplate.opsForSet().isMember("key", "111");
        System.out.println(key);
        Long size = redisTemplate.opsForSet().size("key");
        System.out.println(size);

        return "ok";
    }

    @GetMapping("/testHash")
    public String testHash() {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.flushAll();
            return null;
        });
        redisTemplate.opsForHash().put("key","f1","111");
        redisTemplate.opsForHash().put("key","f2","222");
        redisTemplate.opsForHash().put("key","f3","333");
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("key");
        System.out.println(entries);
        redisTemplate.opsForHash().delete("key","f1");
        Long size = redisTemplate.opsForHash().size("key");
        System.out.println(size);
        Boolean hasKey = redisTemplate.opsForHash().hasKey("key", "f1");
        System.out.println(hasKey);
        return "ok";
    }

    @GetMapping("/testZSet")
    public String testZSet() {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.flushAll();
            return null;
        });
        redisTemplate.opsForZSet().add("key","zhangsan",12);
        redisTemplate.opsForZSet().add("key","lisi",22);
        redisTemplate.opsForZSet().add("key","wangwu",32);
        Set<String> range = redisTemplate.opsForZSet().range("key", 0, -1);
        System.out.println(range);
        Long rank = redisTemplate.opsForZSet().rank("key", "lisi");
        System.out.println(rank);
        redisTemplate.opsForZSet().remove("key","zhangsan");
        Long size = redisTemplate.opsForZSet().size("key");
        System.out.println(size);
        return "ok";
    }


}
