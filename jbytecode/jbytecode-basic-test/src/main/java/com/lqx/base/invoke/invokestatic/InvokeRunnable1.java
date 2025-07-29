package com.lqx.base.invoke.invokestatic;

public class InvokeRunnable1 {


    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        r.run();
    }
}
