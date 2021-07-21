package com.demo.webflux.controller;

import com.demo.webflux.entity.User;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ranges.DocumentRange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: xush
 * @Date: 2020/12/3
 * @Version: v1.0
 */
@Slf4j
@RestController
public class WebFluxController {

    private Flux<String> stringFlux = Flux.empty();

    @GetMapping("/user")
    public Mono<User> getUser() {

        User user = User.builder()
                .id("1")
                .name("user")
                .age(12)
                .build();
        return Mono.just(user);
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable("id") String id, @RequestParam("name") String name) {

        User user = User.builder()
                .id(id)
                .name(name)
                .age(12)
                .build();
        return Mono.just(user);
    }

    @GetMapping(value = "/users")
    public Flux<User> getUsers() {

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            userList.add(User.builder().id(String.valueOf(i)).name("张" + i).age(i).build());
        }

       return Flux.fromIterable(userList);
    }

    @GetMapping(value = "/sse/1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> flux1() {
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    return "flux data--" + i;
                }));
        return result;
    }

    @GetMapping(value = "/sse/2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<User> flux2() {

       return Flux.interval(Duration.ofSeconds(1))
               .map(time -> User.builder().id(time.toString()).name("李").age(12).build());

    }


}
