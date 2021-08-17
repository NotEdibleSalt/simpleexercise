package com.example.springcache.service;

import com.example.springcache.entity.User;
import org.springframework.cache.annotation.*;

/**
 * @author xsh
 * @version 1.0
 * @date 2021-8-16
 */
@CacheConfig
public interface UserService {

    /**
     * value: 缓存key的前缀
     * #id：参数id
     *
     **/
    @Cacheable(value = "user", key = "#id")
    User getUserById(Integer id);

    /**
     *  #result: 方法的返回结果
     *
     **/
    @CachePut(value = "user", key = "#result.id")
    User create(User user);

    /**
     * #a0: 方法的第一个参数， 等同于#arg[0]
     *
     **/
    @CachePut(value = "user", key = "#a0.id")
    User update(User user);

    @CacheEvict(value = "user", key = "#id")
    void del(Integer id);

    @Caching(
            cacheable = {
                    @Cacheable(value= "user::name", key = "#name")
            },
            put = {
                    @CachePut(value = "user::name", key = "#result.id")
            }
    )
    User getUserByName(String name);
}
