package com.example.springcache.service.impl;

import com.example.springcache.entity.User;
import com.example.springcache.repo.UserRepository;
import com.example.springcache.service.UserService;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xsh
 * @version 1.0
 * @date 2021-8-16
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User getUserById(Integer id) {

        log.info("执行getUserById方法，id: {}", id);
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        return userRepository.save(user);
    }

    @Override
    public void del(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {

        log.info("执行getUserByName方法，name: {}", name);
        return userRepository.findByName(name);
    }
}
