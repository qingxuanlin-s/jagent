package com.lqx;

import lombok.Data;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 5:37 PM
 * @Created by 龙川
 ***********************/
@Data
public class WrapperFoo {
    private Foo foo = new Foo();


    public String sayHelloFoo(String s) {
        return foo.sayHelloFoo(s);
    }

}
