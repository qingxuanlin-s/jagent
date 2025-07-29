package com.lqx.base.invoke.invokestatic;

import lombok.SneakyThrows;

import java.lang.invoke.*;

public class CallSiteTest {
    public static String sayHello(String name) {
        return "Hello, " + name + "!";
    }

    public static String sayGoodbye(String name) {
        return "Goodbye, " + name + "!";
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("=== CallSite示例演示 ===\n");

        // 1. 基础CallSite示例
        basicCallSiteExample();

        // 2. 动态方法调用示例
        dynamicMethodCallExample();


    }

    // 1. 基础CallSite示例
    public static void basicCallSiteExample() throws Throwable {
        System.out.println("1. 基础CallSite示例:");

        // 获取MethodHandles.Lookup
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 创建一个简单的方法调用
        MethodHandle sayHello = lookup.findStatic(CallSiteTest.class, "sayHello",
                MethodType.methodType(String.class, String.class));

        // 创建MutableCallSite
        MutableCallSite callSite = new MutableCallSite(sayHello);

        // 获取invoker，这是实际的调用点
        MethodHandle invoker = callSite.dynamicInvoker();

        // 调用
        String result1 = (String) invoker.invoke("World");
        System.out.println("第一次调用: " + result1);

        // 动态改变调用目标
        MethodHandle sayGoodbye = lookup.findStatic(CallSiteTest.class, "sayGoodbye",
                MethodType.methodType(String.class, String.class));
        callSite.setTarget(sayGoodbye);

        // 再次调用，现在会调用不同的方法
        String result2 = (String) invoker.invoke("World");
        System.out.println("改变目标后调用: " + result2);
        System.out.println();
    }

    // Bootstrap方法 - 模拟invokedynamic的bootstrap
    public static CallSite createDynamicCallSite(String methodName, Class<?> returnType, Class<?>... paramTypes)
            throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(returnType, paramTypes);
        MethodHandle target = lookup.findStatic(CallSiteTest.class, methodName, type);
        return new MutableCallSite(target);
    }

    // 2. 动态方法调用示例 - 模拟invokedynamic
    public static void dynamicMethodCallExample() throws Throwable {
        System.out.println("2. 动态方法调用示例:");

        // 创建一个Bootstrap方法，这在实际的invokedynamic中会被调用
        CallSite callSite = createDynamicCallSite("add", int.class, int.class, int.class);
        MethodHandle invoker = callSite.dynamicInvoker();

        // 动态调用
        int result = (int) invoker.invoke(10, 20);
        System.out.println("动态调用add(10, 20) = " + result);

        // 改变调用策略
        if (callSite instanceof MutableCallSite) {
            MutableCallSite mutable = (MutableCallSite) callSite;
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle multiply = lookup.findStatic(CallSiteTest.class, "multiply",
                    MethodType.methodType(int.class, int.class, int.class));
            mutable.setTarget(multiply);

            int result2 = (int) invoker.invoke(10, 20);
            System.out.println("改变为multiply后: " + result2);
        }
        System.out.println();
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }


}
