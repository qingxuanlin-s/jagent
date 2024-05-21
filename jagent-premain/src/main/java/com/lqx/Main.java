package com.lqx;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 11:26 AM
 * @Created by 龙川
 ***********************/
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();

        Class<?> dynamicType = unloadedType.load(Main.class.getClassLoader()).getLoaded();

        Object o = dynamicType.newInstance();

        System.out.println(o);



    }
}