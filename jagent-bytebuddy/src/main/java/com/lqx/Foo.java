package com.lqx;

import lombok.Data;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 5:37 PM
 * @Created by 龙川
 ***********************/
@Data
public class Foo {

    public String sayHelloFoo() {
        return "Hello!";
    }


    public String sayHelloFoo(String name) {
        if (name.contains("Foo")) {
            throw new IllegalArgumentException("Name cannot contain 'Foo'");
        }
        return "Hello!" + name;
    }

}
