package com.lqx

class TestMethodHandle {

    static def add(x){
        return x + 1
    }


    static void main(String[] args) {
        println(add(10))
        println(add(10.5))
        println(add("hello"))
    }
}
