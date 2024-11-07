package com.lqx.interceptor;

import com.lqx.Foo;
import com.lqx.WrapperFoo;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

/**********************
 *
 * @Description TODO
 * @Date 2024/5/21 11:26 AM
 * @Created by 龙川
 ***********************/
public class PluginWrapperMain {
    @SneakyThrows
    public static void main(String[] args) {
        ByteBuddyAgent.install();


        // 动态增强类
        new ByteBuddy()
                .redefine(WrapperFoo.class)
                .visit(Advice.to(Plugin.class).on(ElementMatchers.named("sayHelloFoo")))
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());


        WrapperFoo foo = new WrapperFoo();
        foo.sayHelloFoo("world");

    }
}