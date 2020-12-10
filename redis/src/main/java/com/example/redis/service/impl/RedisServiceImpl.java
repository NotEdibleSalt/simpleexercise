package com.example.redis.service.impl;

import com.example.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xush
 * @Date: 2020/12/10
 * @Version: v1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean hasKey(String key) {

        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Long countExistingKeys(Set<String> keys) {

        return stringRedisTemplate.countExistingKeys(keys);
    }

    @Override
    public Boolean delete(String key) {

        return stringRedisTemplate.delete(key);
    }

    @Override
    public Long delete(Set<String> keys) {

       return stringRedisTemplate.delete(keys);
    }

    @Override
    public Boolean unlink(String key) {

        return stringRedisTemplate.unlink(key);
    }

    @Override
    public Long unlink(Set<String> keys) {

        return stringRedisTemplate.unlink(keys);
    }

    @Override
    public DataType type(String key) {

        return stringRedisTemplate.type(key);
    }

    @Override
    public Set<String> scan(String pattern) {

        return stringRedisTemplate.execute((RedisCallback<Set<String>>) redisConnection -> {

            Set<String> keys = new HashSet<>();
            ScanOptions scanOptions = ScanOptions.scanOptions()
                    .match(pattern)
                    .build();
            Cursor<byte[]> cursor = redisConnection.scan(scanOptions);
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return keys;
        });
    }

    @Override
    public Set<String> scan(String pattern, long count) {

        return stringRedisTemplate.execute((RedisCallback<Set<String>>) redisConnection -> {

            Set<String> keys = new HashSet<>();
            ScanOptions scanOptions = ScanOptions.scanOptions()
                    .match(pattern)
                    .count(count)
                    .build();
            Cursor<byte[]> cursor = redisConnection.scan(scanOptions);

            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return keys;
        });
    }

    @Override
    public String randomKey() {

        return stringRedisTemplate.randomKey();
    }

    @Override
    public void rename(String oldKey, String newKey) {

        stringRedisTemplate.rename(oldKey, newKey);
    }

    @Override
    public Boolean renameIfAbsent(String oldKey, String newKey) {

        return stringRedisTemplate.renameIfAbsent(oldKey, newKey);
    }

    @Override
    public Boolean expire(String key, long time, TimeUnit timeUnit) {

        return stringRedisTemplate.expire(key, time, timeUnit);
    }

    @Override
    public Boolean expireAt(String key, Date date) {

        return stringRedisTemplate.expireAt(key, date);
    }

    /**
     * 移除给定 key 的过期时间，使得 key 永不过期
     *
     * @param key :
     * @return java.lang.Boolean
     * @Date 2020/12/10
     **/
    @Override
    public Boolean persist(String key) {

        return stringRedisTemplate.persist(key);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {

        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public void watch(String key) {

        stringRedisTemplate.watch(key);
    }

    @Override
    public void watch(Set<String> keys) {

        stringRedisTemplate.watch(keys);
    }

    @Override
    public void unwatch() {

        stringRedisTemplate.unwatch();
    }

    @Override
    public void multi() {

        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.multi();
    }

    @Override
    public void discard() {

        stringRedisTemplate.discard();
        stringRedisTemplate.setEnableTransactionSupport(false);
    }

    @Override
    public List<Object> exec() {

        List<Object> exec = stringRedisTemplate.exec();
        stringRedisTemplate.setEnableTransactionSupport(false);

        return exec;
    }
}
