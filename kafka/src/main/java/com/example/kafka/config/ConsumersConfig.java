package com.example.kafka.config;

import com.example.kafka.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.management.Sensor;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author: xush
 * @Date: 2020-12-26
 * @Version: v1.0
 */
@Configuration
public class ConsumersConfig {

    /**
     * 对应yml中配置的logC-in-0通道，即topic log。
     * 消费topic log中的消息
     *
     * @return java.util.function.Consumer<com.example.kafka.entity.Person>
     * @Date 2020-12-27
     **/
    @Bean
    public Consumer<Person> logC() {

        return person -> {
            System.out.println("Received: " + person);
        };
    }

    /**
     * 对应yml中配置的addAgeC-in-0通道，即topic addAge。
     * 消费topic addAge中的消息
     *
     * @return java.util.function.Consumer<com.example.kafka.entity.Person>
     * @Date 2020-12-27
     **/
    @Bean
    public Consumer<Person> addAgeC(){

        return person -> {

            person.setAge(person.getAge() + 10);
            System.out.println("Consumer addAge: " + person.toString());
        };
    }
}
