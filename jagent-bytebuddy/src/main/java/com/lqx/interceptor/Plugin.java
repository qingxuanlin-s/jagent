package com.lqx.interceptor;

import net.bytebuddy.asm.Advice;

import java.util.Arrays;

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
        System.out.println("Entering method: " + method);
        System.out.println("Arguments: " + Arrays.toString(args));
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Origin String method) {
        System.out.println("Exiting method: " + method);
    }

}
