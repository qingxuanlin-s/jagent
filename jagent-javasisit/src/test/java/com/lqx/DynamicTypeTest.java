package com.lqx;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class DynamicTypeTest {

    @Test
    @DisplayName("动态生成拦截新方法，返回固定值")
    @SneakyThrows
    void testDynamicTypePositive() throws IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.lqx.Foo");

        CtMethod personFly = cc.getDeclaredMethod("sayHelloFoo");
        personFly.setBody("{return \"Hello, modified!\";}");
        cc.toClass();
        Foo myClass = new Foo();
        assertEquals("Hello, modified!", myClass.sayHelloFoo());
    }



}