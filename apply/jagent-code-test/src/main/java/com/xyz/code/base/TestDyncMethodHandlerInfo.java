package com.xyz.code.base;
//动态方法处理器信息测试类
public class TestDyncMethodHandlerInfo {
    public void test() {
        new Thread(() -> {
            System.out.println("Hello World!");
        });
    }
}
