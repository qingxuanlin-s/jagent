package com.lqx.base.invoke.invokestatic;

public class InvokeRunnable {


    public static void main(String[] args) {
        Runnable r = () -> System.out.println("Hello");
        r.run();
    }
}
