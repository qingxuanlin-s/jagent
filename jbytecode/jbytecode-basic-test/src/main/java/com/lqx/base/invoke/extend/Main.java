package com.lqx.base.invoke.extend;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Child child = new Child();
        child.method1();

        TimeUnit.SECONDS.sleep(10000);
    }
}
