package com.lqx.base.invoke.invokestatic;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class LambdaSimpleCallSite {
    @SneakyThrows
    public static void main(String[] args) {
        Function<String,String> function = s -> s.toUpperCase();
        System.out.println(
                function.apply("hello")
        );

        TimeUnit.SECONDS.sleep(1000);
    }
}

