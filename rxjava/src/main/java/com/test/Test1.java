package com.test;

import rx.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by davor on 19/10/15.
 */
public class Test1 {


    public static void main(String[] args) {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");
        Observable<String> observable = Observable.from(list);
        observable.subscribe(
                element -> {
                    try {
                        System.out.println(element + "  " + Thread.currentThread().getId());
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.err.println(error);
                },
                () -> System.out.println("We've finnished!"));
    }
}
