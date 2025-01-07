package com.xyz.code;


import java.util.concurrent.TimeUnit;

public class TestCodeInv {
    @lombok.SneakyThrows
    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {
                }

                TestCode1 testCode1 = new TestCode1();
                testCode1.realPrt(new TestCode());
            }
        },"线程1").start();


        new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {
                }

                TestCode2 testCode2 = new TestCode2();
                testCode2.realPrt(new TestCode());
            }
        },"线程2").start();



        TimeUnit.SECONDS.sleep(500);
    }
}
