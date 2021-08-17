package com.example.springcache.repo;

import com.example.springcache.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xsh
 * @version 1.0
 * @date 2021-8-16
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
}
