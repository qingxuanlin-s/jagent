package com.lqx.base.hignlevel;

import java.util.List;

public class GenericTypeTest {
    //泛型擦除
//    public void print(List<String> lists){
//
//    }

    public  void print(List<Integer> lists){

    }

    static class Pair<T>{
        private T t;

        public  Pair(T t){
           this.t = t;
        }

    }

    public static void main(String[] args) {
        Pair<Integer> pair1 = new Pair(new Object());

    }
}
