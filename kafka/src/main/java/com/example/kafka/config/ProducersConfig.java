package com.example.kafka.config;

import com.example.kafka.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.handler.annotation.SendTo;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * @Author: xush
 * @Date: 2020-12-26
 * @Version: v1.0
 */
@Configuration
public class ProducersConfig {

    private BlockingQueue<Person> unbounded = new LinkedBlockingQueue<>();

    /**
     * 对应yml中配置的logP-out-0通道，即topic log
     * @return java.util.function.Supplier<com.example.kafka.entity.Person>
     * @Date 2020-12-27
     **/
    @Bean
    public Supplier<Person> logP(){
        return () -> unbounded.poll();
    }

    /**
     * 调用本方法向log topic发送消息
     *
     * @param person:
     * @return void
     * @Date 2020-12-27
     **/
    public void log(Person person){
        unbounded.offer(person);
    }
}
