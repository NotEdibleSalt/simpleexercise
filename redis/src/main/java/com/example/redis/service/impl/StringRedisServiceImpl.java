package com.example.redis.service.impl;

import com.example.redis.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xush
 * @Date: 2020/12/10
 * @Version: v1.0
 */
@Service
public class StringRedisServiceImpl extends RedisServiceImpl implements StringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void set(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long time, TimeUnit timeUnit) {

        stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public Boolean setIfAbsent(String key, String value) {

        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Boolean setIfAbsent(String key, String value, long time, TimeUnit timeUnit) {

        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    @Override
    public Boolean setIfPresent(String key, String value) {

        return stringRedisTemplate.opsForValue().setIfPresent(key, value);
    }

    @Override
    public Boolean setIfPresent(String key, String value, long time, TimeUnit timeUnit) {

        return stringRedisTemplate.opsForValue().setIfPresent(key, value, time, timeUnit);
    }

    @Override
    public void multiSet(Map<? extends String, ? extends String> map) {

        stringRedisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public Boolean multiSetIfAbsent(Map<? extends String, ? extends String> map) {

        return stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    @Override
    public String get(String key) {

        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndSet(String key, String value) {

        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public List<String> multiGet(List<String> keys) {

        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public Long increment(String key) {

        return stringRedisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long increment(String key, long value) {

        return stringRedisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public Double increment(String key, double value) {

        return stringRedisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public Long decrement(String key) {

        return stringRedisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(String key, long value) {

        return stringRedisTemplate.opsForValue().decrement(key, value);
    }

    @Override
    public Integer append(String key, String value) {

        return stringRedisTemplate.opsForValue().append(key, value);
    }

    @Override
    public Long size(String key) {

        return stringRedisTemplate.opsForValue().size(key);
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {

        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(String key, long offset) {

        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public List<Long> bitField(String key, BitFieldSubCommands bitFieldSubCommands) {

        return stringRedisTemplate.opsForValue().bitField(key, bitFieldSubCommands);
    }

}
