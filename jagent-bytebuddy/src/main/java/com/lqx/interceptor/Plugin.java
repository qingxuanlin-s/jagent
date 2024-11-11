package com.lqx.interceptor;

import net.bytebuddy.asm.Advice;

import java.util.Arrays;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

public class Plugin {

    /**
     * 在方法执行前进行切面
     *
     * @param method     目标方法
     * @return 方法执行返回的临时数据
     * @since 0.0.1
     */
    //@RuntimeType
    @Advice.OnMethodEnter()
    public static void  beforeMethod(
            @Advice.Origin String method, @Advice.AllArguments Object[] args) {
        System.out.println("进入方法: " + method);
        System.out.println("参数: " + Arrays.toString(args));
    }

//    @Advice.OnMethodExit
//    public static void onExit(@Advice.Origin String method,@Advice.Return Object result
//            ) {
//        System.out.println("退出方法: " + method);
//    }
//

    @Advice.OnMethodExit(onThrowable = Throwable.class,inline = false)
    public static void onExitWithException(@Advice.Origin String method,@Advice.Return Object result,
                                           @Advice.Thrown Throwable exceptions
            ){
        System.out.println("退出方法: " + method + " 响应: " + result + " 异常: " + exceptions);
    }



}
