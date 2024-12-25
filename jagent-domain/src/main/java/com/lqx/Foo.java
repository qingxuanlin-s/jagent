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

    /***
     *
     *  // access flags 0x1
     *    public sayHelloFoo()Ljava/lang/String;
     *    L0
     *     LINENUMBER 28 L0
     *     LDC "Hello!"
     *     ARETURN
     *    L1
     *     LOCALVARIABLE this Lcom/lqx/Foo; L0 L1 0
     *     MAXSTACK = 1
     *     MAXLOCALS = 1
     * */
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
