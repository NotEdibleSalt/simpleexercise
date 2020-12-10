package com.example.redis.service;


import org.springframework.data.redis.connection.BitFieldSubCommands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xush
 * @Date: 2020/12/10
 * @Version: v1.0
 */
public interface StringRedisService extends RedisService {

    void set(String key, String value);

    void set(String key, String value, long time, TimeUnit timeUnit);

    Boolean setIfAbsent(String key, String value);

    Boolean setIfAbsent(String key, String value, long time, TimeUnit timeUnit);

    Boolean setIfPresent(String key, String value);

    Boolean setIfPresent(String key, String value, long time, TimeUnit timeUnit);

    void multiSet(Map<? extends String, ? extends String> map);
    
    Boolean multiSetIfAbsent(Map<? extends String, ? extends String> map);
    
    String get(String key);

    String getAndSet(String key, String value);
    
    List<String> multiGet(List<String> keys);
    
    Long increment(String key);

    Long increment(String key, long value);
    
    Double increment(String key, double value);

    Long decrement(String key);

    Long decrement(String key, long value);
    
    Integer append(String key, String value);
    
    Long size(String key);
    
    Boolean setBit(String key, long offset, boolean value);
    
    Boolean getBit(String key, long value);
    
    List<Long> bitField(String key, BitFieldSubCommands bitFieldSubCommands);
    
}
