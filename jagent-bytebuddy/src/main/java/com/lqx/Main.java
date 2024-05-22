package com.lqx;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.concurrent.TimeUnit;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 11:26 AM
 * @Created by 龙川
 ***********************/
public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        Foo f = new Foo();
        System.out.println(f.sayHelloFoo());


        TimeUnit.SECONDS.sleep(10000);
    }
}