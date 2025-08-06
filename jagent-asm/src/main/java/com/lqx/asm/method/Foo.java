package com.lqx.asm.method;


public class Foo {
    public int math(int x){
        return x + 1;
    }

    public static void main(String[] args) {
        System.out.println(new Foo().math(1));
    }
}

