package com.lqx;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ReCodeUtil {

    @SneakyThrows
    public static void recode() {
        ByteBuddyAgent.install();
        new ByteBuddy()
                .redefine(Foo.class)
                .method(named("sayHelloFoo"))
                .intercept(FixedValue.value("Hello Foo Redefined"))
                .make()
                .load(Foo.class.getClassLoader(),
                        ClassReloadingStrategy.fromInstalledAgent());

        Foo f = new Foo();
        System.out.println(f.sayHelloFoo());

    }

}
