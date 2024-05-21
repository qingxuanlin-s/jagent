package com.lqx;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class DynamicTypeTest {

    @Test
    @DisplayName("动态生成拦截新方法，返回固定值")
    void testDynamicTypePositive() throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();
        Class<?> dynamicType = unloadedType.load(Main.class.getClassLoader()).getLoaded();
        Object o = dynamicType.newInstance();
        assertEquals("Hello World ByteBuddy!", o.toString());
    }



    @Test
    @DisplayName("代理父类")
    void testProxy() throws IllegalAccessException, InstantiationException {
        String r = new ByteBuddy()
                .subclass(Foo.class)
                .method(named("sayHelloFoo")
                        .and(isDeclaredBy(Foo.class)
                                .and(returns(String.class))))
                .intercept(MethodDelegation.to(Bar.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .sayHelloFoo();

        assertEquals(r, Bar.sayHelloBar());

        System.out.println(r);
    }


    @Test
    @DisplayName("重新加载class")
    void testReloadClass() {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("sayHelloFoo"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(Foo.class.getClassLoader(),
                        ClassReloadingStrategy.fromInstalledAgent());

        Foo f = new Foo();
        assertEquals(f.sayHelloFoo(), "Hello Foo Redefined");
    }



}