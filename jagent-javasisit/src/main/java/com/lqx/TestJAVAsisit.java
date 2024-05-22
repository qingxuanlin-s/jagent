package com.lqx;

import lombok.SneakyThrows;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TestJAVAsisit {

    @SneakyThrows
    public static void main(String[] args) {
        Foo myClass = new Foo();
        System.out.println(myClass.sayHelloFoo());

        TimeUnit.SECONDS.sleep(10000);
    }
}
