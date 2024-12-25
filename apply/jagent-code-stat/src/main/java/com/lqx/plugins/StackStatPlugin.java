package com.lqx.plugins;

import com.lqx.plugins.collect.DisruptorCollect;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

public class StackStatPlugin {



        @Advice.OnMethodEnter(inline = false)
        public static void  beforeMethod(
                @Advice.Origin Method method, @Advice.AllArguments Object[] args) {
            DisruptorCollect.putStacks(method.toString(), Thread.currentThread().getStackTrace());
        }


        @Advice.OnMethodExit(onThrowable = Throwable.class,inline = false)
        public static void onExitWithException(@Advice.Origin Method method,
                                               @Advice.Return(typing = DYNAMIC) Object result, @Advice.Thrown Throwable exceptions
        ){
            System.out.println("退出方法: " + method + " 响应: " + result + " 异常: " + exceptions);
        }







}
