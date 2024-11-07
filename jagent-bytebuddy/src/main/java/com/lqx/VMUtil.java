package com.lqx;

import lombok.SneakyThrows;

/**
 * 推荐
 * @author     : longchuan
 * @date       : 2022/3/11 3:52 下午
 * @description:
 * @version    :
 */
public class VMUtil {



    @SneakyThrows
    public static String jstack(String keyword) {
        return jstack(Thread.currentThread().getStackTrace(),keyword);
    }

    public static String jstack(StackTraceElement[] stackTraceElements,String keyword) {
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < stackTraceElements.length; i++) {
                StackTraceElement stackTraceElement = stackTraceElements[i];
                String string = stackTraceElement.toString();
                if(string.contains(keyword) && !string.contains("VMUtil")) {
                    return string;
                }
            }
        } catch (Throwable e) {

        }

        return result.toString();
    }



}
