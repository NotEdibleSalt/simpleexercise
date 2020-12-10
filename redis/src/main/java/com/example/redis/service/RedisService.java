package com.example.redis.service;

import org.springframework.data.redis.connection.DataType;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xush
 * @Date: 2020/12/10
 * @Version: v1.0
 */
public interface RedisService {

    Boolean hasKey(String key);

    Long countExistingKeys(Set<String> keys);

    Boolean delete(String key);

    Long delete(Set<String> keys);

    Boolean unlink(String key);

    Long unlink(Set<String> keys);

    DataType type(String key);

    Set<String> scan(String pattern);

    Set<String> scan(String pattern, long count);

    String randomKey();

    void rename(String oldKey, String newKey);

    Boolean renameIfAbsent(String oldKey, String newKey);

    Boolean expire(String key, long time, TimeUnit timeUnit);

    Boolean expireAt(String key, Date date);

    /**
     * 移除给定 key 的过期时间，使得 key 永不过期
     *
     * @param key:
     * @return java.lang.Boolean
     * @Date 2020/12/10
     **/
    Boolean persist(String key);

    Long getExpire(String var1, TimeUnit timeUnit);

    void watch(String key);

    void watch(Set<String> keys);

    void unwatch();

    void multi();

    void discard();

    List<Object> exec();

}
