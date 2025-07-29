package com.lqx.base.invoke.invokestatic;


import lombok.SneakyThrows;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlerTest {

    public void handle() {
        System.out.println("MethodHandlerTest.handle()");
    }


    @SneakyThrows
    public static void main(String[] args) {
        MethodHandlerTest test = new MethodHandlerTest();
        MethodHandle handle = MethodHandles.lookup().findVirtual(
                MethodHandlerTest.class, "handle", MethodType.methodType(void.class));
        handle.invokeExact(test);
    }
}
