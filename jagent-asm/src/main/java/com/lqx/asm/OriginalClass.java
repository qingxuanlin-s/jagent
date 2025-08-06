package com.lqx.asm;

public class OriginalClass {

    public void sayHello() {
        System.out.println("Hello, ASM!");
        // 模拟方法执行耗时
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OriginalClass oc = new OriginalClass();
        oc.sayHello();
    }
}