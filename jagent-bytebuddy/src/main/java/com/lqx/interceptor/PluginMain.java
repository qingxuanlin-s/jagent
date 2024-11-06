package com.lqx.interceptor;

import com.lqx.Foo;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.concurrent.TimeUnit;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 11:26 AM
 * @Created by 龙川
 ***********************/
public class PluginMain {
    @SneakyThrows
    public static void main(String[] args) {
        ByteBuddyAgent.install();

        // 保存原始类定义
        Class<?> fooClass = Foo.class;
        byte[] originalClassDefinition = new ByteBuddy()
                .redefine(fooClass)
                .make()
                .getBytes();

        // 动态增强类
        new ByteBuddy()
                .redefine(Foo.class)
                .visit( Advice.to(Plugin.class).on(ElementMatchers.named("sayHelloFoo")))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());


        Foo foo = new Foo();
        foo.sayHelloFoo("Foo");

        // 动态撤销增强
        new ByteBuddy()
                .redefine(fooClass, ClassFileLocator.Simple.of(fooClass.getName(), originalClassDefinition))
                .make()
                .load(fooClass.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        foo.sayHelloFoo("world");
        new Foo().sayHelloFoo("world");
    }
}