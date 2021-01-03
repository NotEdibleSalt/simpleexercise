package com.demo.webflux.flux;

import com.demo.webflux.subscriber.SampleSubscriber;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Processor;
import org.springframework.web.bind.annotation.PutMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @Author: xush
 * @Date: 2021-1-3
 * @Version: v1.0
 */
public class FluxTest {

    @Test
    public void create1() {
        Flux.range(1, 10).subscribe(System.out::println);
    }

    @Test
    public void create2() {
        Flux.just("a", "b").subscribe(System.out::println);
    }

    @Test
    public void create3() {
        Flux.fromStream(Stream.of("a", "b")).subscribe(System.out::println);
    }

    @Test
    public void create4() {

        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });
        flux.subscribe(System.out::println);
    }

    @Test
    public void create5() {

        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                });
        flux.subscribe(System.out::println);
    }

    @Test
    public void create6() {

        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.subscribe();

        Flux.create(fluxSink -> {

        });
    }

    @Test
    public void subscribe1() {

        Flux.range(1, 4)
                .subscribe(i -> System.out.println(i),
                        error -> System.err.println("Error " + error),
                        () -> System.out.println("Done"));
    }

    @Test
    public void subscribe2() {

        Flux.range(1, 4)
                .subscribe(i -> {
                            if (i == 2) {
                                throw new RuntimeException("处理失败");
                            }
                            System.out.println(i);
                        },
                        error -> System.err.println("Error " + error),
                        () -> System.out.println("Done"));
    }

    @Test
    public void subscribe3() {

        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> ints = Flux.range(1, 30);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> {
                    System.out.println("Done");
                });
        ints.subscribe(ss);
    }

    @Test
    public void interval1() {

        Set set = new HashSet();
        Flux.range(1, 10000)
                .publishOn(Schedulers.parallel())
                .subscribe(x -> {
                            set.add(Thread.currentThread().getName());
                            System.out.println(x);
                        }, null,
                        () -> System.out.println(set));

    }

    @Test
    public void error1() {
        Flux.just(10)
                .map(integer -> {
                    throw new RuntimeException("失败");
                })
                .onErrorReturn("RECOVERED")
                .subscribe(System.out::println);
    }

    @Test
    public void error2() {
        Flux.just(10)
                .map(integer -> {
                    throw new RuntimeException("失败");
                })
                .onErrorReturn(s -> s.getMessage().equals("失败"), "aaaa")
                .subscribe(System.out::println);
    }

    @Test
    public void errorResume1() {

        Flux.just("key1", "key2")
                .flatMap(k -> {
                    if (k.equals("key2")) {
                        throw new RuntimeException("失败");
                    }
                    return Flux.just(k);
                }).onErrorResume(e -> Flux.just(e.getMessage()))
                .subscribe(System.out::println);
    }

    @Test
    public void errorMap() {

        Flux.just("key1", "key2")
                .flatMap(k -> {
                    if (k.equals("key2")) {
                        throw new RuntimeException("失败");
                    }
                    return Flux.just(k);
                }).onErrorMap(e -> new RuntimeException("处理失败"))
                .subscribe(System.out::println);
    }

    @Test
    public void transform() {

        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: " + d));

    }

    @SneakyThrows
    @Test
    public void ConnectableFlux() {

        Flux<Integer> source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));

        Flux<Integer> autoCo = source.publish().autoConnect(2);

        autoCo.subscribe(System.out::println, e -> {
        }, () -> {
        });
        System.out.println("subscribed first");
        Thread.sleep(500);
        System.out.println("subscribing second");
        autoCo.subscribe(System.out::println, e -> {
        }, () -> {
        });
    }

    @Test
    public void groupBy() {
        Flux<String> flux = Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                .concatMap(g -> g.defaultIfEmpty(-1) //如果组为空，显示为 -1
                        .map(String::valueOf) //转换为字符串
                        .startWith(g.key()));//以该组的 key 开头

        flux.subscribe(System.out::println);
    }

    @Test
    public void window(){

        Flux.range(1, 10)
                .window(5, 3) //overlapping windows
                .concatMap(g -> g.defaultIfEmpty(-1)) //将 windows 显示为 -1
                .subscribe(System.out::println);
    }

    @Test
    public void windowWhile(){

        Flux.just(1, 3, 5, 2, 4, 7, 6, 11, 12, 13)
                .windowWhile(i -> i % 2 == 0)
                .concatMap(g -> g.defaultIfEmpty(-1))
                .subscribe(System.out::println);
    }

    @Test
    public void buffer(){

        Flux.range(1, 10)
                .buffer(5, 3)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferWhile(){

        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13, 7, 22)
                .bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

    @Test
    public void parallel(){

        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }

    @Test
    public void zipWith(){

        Flux.range(1, 10)
                .zipWith(Flux.just("a", "b", "c"), (a, b) -> a + b)
                .subscribe(i -> System.out.println(i));
    }

}
