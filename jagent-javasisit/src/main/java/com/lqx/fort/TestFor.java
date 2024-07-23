package com.lqx.fort;

import com.google.common.collect.Lists;

import java.util.List;

public class TestFor {


    public static void main(String[] args) {
        System.out.println(new TestFor().hello(Lists.newArrayList(1,2,3,4),new StringBuilder("ceshi")));
    }

    public  String hello(List<Integer> x,StringBuilder yBuilder){
        for (Integer integer : x) {       yBuilder.append(" ").append(integer);  }
        return yBuilder.toString();
    }


}