package com.lqx.plugins;

import com.lqx.plugins.collect.StackCollect;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class StackStatPlugin {



        @Advice.OnMethodEnter(inline = false)
        public static void  beforeMethod(
                @Advice.Origin Method method, @Advice.AllArguments Object[] args) {
            try {
                StackCollect.putStacks(method.toString(), Thread.currentThread().getStackTrace());
            } catch (Exception e){
                e.printStackTrace();
            }
        }


//        @Advice.OnMethodExit(onThrowable = Throwable.class,inline = false)
//        public static void onExitWithException(@Advice.Origin Method method,
//                                               @Advice.Return(typing = DYNAMIC) Object result, @Advice.Thrown Throwable exceptions
//        ){
//            System.out.println("退出方法: " + method + " 响应: " + result + " 异常: " + exceptions);
//        }







}
