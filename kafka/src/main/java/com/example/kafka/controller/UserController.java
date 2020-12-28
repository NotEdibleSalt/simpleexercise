package com.example.kafka.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.kafka.config.ProducersConfig;
import com.example.kafka.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: xush
 * @Date: 2020-12-21
 * @Version: v1.0
 */
@RestController
public class UserController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private ProducersConfig producersConfig;

    @PostMapping("/log")
    public void log(@RequestBody Person person){

        producersConfig.log(person);
    }

    @PostMapping("/addAge")
    public boolean addAge(@RequestBody Person person){

        person.setAge(RandomUtil.randomInt(10, 90));
        person.setSuccess(RandomUtil.randomBoolean());
        person.setBirthday(new Date());

        System.out.println("Producer addAge: " + person.toString());
        return streamBridge.send("addAge", person);
    }

    @PostMapping("/ann")
    @SendTo("SendTo")
    public Person annSendTo(@RequestBody Person person){

        person.setAge(RandomUtil.randomInt(10, 90));
        person.setSuccess(RandomUtil.randomBoolean());
        person.setBirthday(new Date());

//        producersConfig.annSendTo(person);

        return person;
    }

}
