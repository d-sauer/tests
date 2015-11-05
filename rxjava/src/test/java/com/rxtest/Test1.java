package com.rxtest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by davor on 19/10/15.
 *
 * http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/
 * http://blog.danlew.net/2014/09/22/grokking-rxjava-part-2/
 * http://blog.danlew.net/2014/09/30/grokking-rxjava-part-3/
 */
public class Test1 {

    public static final Logger logger = LoggerFactory.getLogger(Test1.class);

    @Test
    public void simpleStream() {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");
        Observable<String> observable = Observable.from(list);
        observable.subscribe(
                element -> {
                    try {
                        logger.info("Element: {}", element);
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        logger.error("Stream error", e);
                    }
                },
                error -> {
                    logger.error("Stream error", error);
                },
                () -> logger.info("End of stream"));
    }

    @Test
    public void hello() {
        Observable
                .just("Hello, world!")
                .subscribe(System.out::println);
    }

    @Test
    public void hello2() {
        Observable
                .just("Hi, there!")
                .map(s -> s + " - Davor")
                .subscribe(System.out::println);
    }

    @Test
    public void hello3() {
        Observable
                .just("Hi, there!")
                .map(s -> s.hashCode())
                .subscribe(data -> logger.info("Data: {}", data));
    }

    @Test
    public void hello4() {
        Observable<List<String>> result = query("test");

        result.subscribe(data -> {
            data.forEach(d -> logger.info("Data: {}", d));
        });

        System.out.println("---- remove for loop ------");

        result.subscribe(urls -> {
            Observable.from(urls)
                    .subscribe(url -> logger.info("URL: {}", url));
        });

        System.out.println("---- flatMap ------");

        result.flatMap(strings -> Observable.from(strings))
                .subscribe(url -> logger.info("URL: {}", url));

        System.out.println("---- flatMap to Num------");

    }

    private Observable<List<String>> query(String text) {
        List<List<String>> result = new ArrayList<>();
        result.add(Arrays.asList(urlMaker.apply(text + "A"), urlMaker.apply(text + "A")));
        result.add(Arrays.asList(urlMaker.apply(text + "B"), urlMaker.apply(text + "B")));

        return Observable.from(result);
    }

    private Function<String, String> urlMaker = url -> "http://" + url + "/" + Math.abs(new Random().nextInt(20));

    private Function<String, Integer> extractInt = str -> Integer.parseInt(str.substring(str.lastIndexOf("/") + 1));

}
