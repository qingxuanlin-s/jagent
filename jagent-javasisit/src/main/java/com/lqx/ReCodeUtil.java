package com.lqx;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.SneakyThrows;

public class ReCodeUtil {

    @SneakyThrows
    public static void recode() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.lqx.Foo");

        CtMethod personFly = cc.getDeclaredMethod("sayHelloFoo");
        personFly.setBody("{return \"Hello, modified!\";}");
        cc.toClass();
        Foo myClass = new Foo();
        System.out.println(myClass.sayHelloFoo());

    }

}
