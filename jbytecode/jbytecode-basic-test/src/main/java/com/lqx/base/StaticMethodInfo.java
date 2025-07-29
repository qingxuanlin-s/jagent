package com.lqx.base;

public class StaticMethodInfo {
    public static int count = 0;


    {
        count++;
    }

    static {
        System.out.println(count);
    }
}
