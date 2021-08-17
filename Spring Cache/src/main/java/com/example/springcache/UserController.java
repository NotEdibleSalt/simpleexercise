package com.example.springcache;

import com.example.springcache.entity.User;
import com.example.springcache.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xsh
 * @version 1.0
 * @date 2021-8-17
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable("id") Integer id) {

        return userService.getUserById(id);
    }

    @PostMapping("users")
    public User create(@RequestBody User user) {

        return userService.create(user);
    }

    @PutMapping("users")
    public User update(@RequestBody User user) {

        return userService.update(user);
    }

    @DeleteMapping("users/{id}")
    public void del(@PathVariable("id") Integer id) {

        userService.del(id);
    }

    @GetMapping("users")
    public User getUserByName(@RequestParam("name") String name) {

        return userService.getUserByName(name);
    }
}
