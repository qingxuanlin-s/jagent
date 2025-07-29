package com.lqx.base;

public class StaticMethodInfoMain {
    public static void main(String[] args) {
        System.out.println(StaticMethodInfo.count);

        System.out.println(new StaticMethodInfo().count);

        System.out.println(new StaticMethodInfo());
    }
}
