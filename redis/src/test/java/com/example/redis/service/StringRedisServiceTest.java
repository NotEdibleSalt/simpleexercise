package com.example.redis.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StringRedisServiceTest {

    @Autowired
    private StringRedisService stringRedisService;

    @Test
    void set() {

        stringRedisService.set("key1", "value1");
    }

    @Test
    void setIfAbsent() {

        stringRedisService.setIfAbsent("key2", "value2");
    }

    @Test
    void setIfPresent() {

        stringRedisService.setIfPresent("key2", "value2-IfPresent");
    }


    @Test
    void get() {

        String key2 = stringRedisService.get("key2");
        assert key2 == "value2-IfPresent";
    }

    @Test
    void decrement() {

        long num = stringRedisService.decrement("num");
        assert num == 1;

    }

    @Test
    void append() {

        stringRedisService.append("key1", "append");
        String key1 = stringRedisService.get("key1");
        assert key1.contains("append") && key1.contains("value1");
    }

    @Test
    void getBit() {

        boolean key1 = stringRedisService.getBit("key1", 3);
        System.out.println("key1: " + key1);

    }

    @Test
    void transactional() {

        stringRedisService.multi();
        stringRedisService.set("t111", "10");
        stringRedisService.increment("t1");
        stringRedisService.set("t211", "10");
        stringRedisService.increment("t2", 10);
        stringRedisService.set("age1", "26");
        stringRedisService.set("name1", "张三");
        stringRedisService.increment("name1");
        stringRedisService.set("当当当", "敲门了");
        stringRedisService.exec();

    }

    @Test
    void scan(){

        Set<String> stringSet = stringRedisService.scan("*");
        stringSet.forEach(System.out::println);
    }

}