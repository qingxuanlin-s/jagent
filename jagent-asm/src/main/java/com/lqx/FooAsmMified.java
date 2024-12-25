package com.lqx;

import lombok.Data;

import java.util.Arrays;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 5:37 PM
 * @Created by 龙川
 ***********************/
@Data
public class FooAsmMified {

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
    public String sayHelloFooAop() {
        try {
            System.out.println("进入方法: " + "sayHelloFoo");
            return sayHelloFoo();
        }finally {
            System.out.println("退出方法: " + "sayHelloFoo");
        }
    }



}
