package com.lqx.base.invoke.invokestatic;

import java.lang.invoke.*;
import java.util.function.*;
import java.util.Arrays;
import java.util.List;

/**
 * Lambda表达式的CallSite实现详解
 * 展示Lambda如何通过invokedynamic和CallSite工作
 */
public class LambdaCallSiteExample {
    
    public static void main(String[] args) throws Throwable {
        // 2. 手动实现lambda的CallSite机制
        manualLambdaImplementation();
        
        // 4. Lambda的性能对比
        lambdaPerformanceComparison();
        
        // 5. 复杂lambda的实现
        complexLambdaExample();
        
        // 6. Method Reference的实现
        methodReferenceExample();
    }
    

    
    // 2. 手动实现lambda的CallSite机制
    public static void manualLambdaImplementation() throws Throwable {
        System.out.println("2. 手动实现Lambda CallSite机制:");
        
        // 模拟LambdaMetafactory.metafactory的工作
        CallSite lambdaCallSite = createLambdaCallSite();
        MethodHandle lambdaFactory = lambdaCallSite.dynamicInvoker();
        
        // 创建lambda实例
        Function<String, String> lambda = (Function<String, String>) lambdaFactory.invoke();
        
        // 使用lambda
        String result = lambda.apply("manual implementation");
        System.out.println("手动实现的Lambda结果: " + result);
        
        // 展示内部结构
        System.out.println("Lambda实例类型: " + lambda.getClass().getName());
        System.out.println("是否为合成类: " + lambda.getClass().isSynthetic());
        System.out.println();
    }
    
    // 创建Lambda的CallSite - 模拟LambdaMetafactory
    public static CallSite createLambdaCallSite() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        
        // 1. 定义接口方法的签名
        MethodType interfaceMethodType = MethodType.methodType(Object.class, Object.class);
        
        // 2. 获取实际的实现方法句柄
        MethodHandle implementation = lookup.findStatic(LambdaCallSiteExample.class, 
            "lambdaImplementation", MethodType.methodType(String.class, String.class));
        
        // 3. 定义实际方法的签名
        MethodType actualMethodType = MethodType.methodType(String.class, String.class);
        
        // 4. 使用LambdaMetafactory创建CallSite
        return LambdaMetafactory.metafactory(
            lookup,                    // caller lookup
            "apply",                   // interface method name
            MethodType.methodType(Function.class), // factory method type
            interfaceMethodType,       // interface method signature
            implementation,            // implementation method handle
            actualMethodType          // actual method signature
        );
    }
    
    // Lambda的实际实现方法（编译器生成的合成方法）
    public static String lambdaImplementation(String input) {
        return "手动处理: " + input.toUpperCase();
    }
    

    

    // 4. Lambda性能对比
    public static void lambdaPerformanceComparison() {
        System.out.println("4. Lambda性能对比:");
        
        int iterations = 10_000_000;
        
        // Lambda表达式
        Function<Integer, Integer> lambda = x -> x * 2;
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            lambda.apply(i);
        }
        long lambdaTime = System.nanoTime() - startTime;
        
        // 匿名内部类
        Function<Integer, Integer> anonymous = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 2;
            }
        };

        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            anonymous.apply(i);
        }
        long anonymousTime = System.nanoTime() - startTime;
        
        // 直接方法调用
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            multiplyByTwo(i);
        }
        long directTime = System.nanoTime() - startTime;
        
        System.out.println("Lambda表达式耗时: " + lambdaTime / 1_000_000 + " ms");
        System.out.println("匿名内部类耗时: " + anonymousTime / 1_000_000 + " ms");
        System.out.println("直接方法调用耗时: " + directTime / 1_000_000 + " ms");
        System.out.println("Lambda vs 直接调用: " + (lambdaTime / (double) directTime) + "x");
        System.out.println();
    }
    
    public static Integer multiplyByTwo(Integer x) {
        return x * 2;
    }
    
    // 5. 复杂lambda实现 - 闭包捕获
    public static void complexLambdaExample() throws Throwable {
        System.out.println("5. 复杂Lambda - 闭包捕获:");
        
        String prefix = "处理结果: ";
        int multiplier = 10;
        
        // 捕获局部变量的lambda
        Function<Integer, String> complexLambda = x -> prefix + (x * multiplier);
        
        String result = complexLambda.apply(5);
        System.out.println("闭包Lambda结果: " + result);
        
        System.out.println("\n闭包捕获的实现机制:");
        System.out.println("1. 编译器生成构造函数接收捕获的变量");
        System.out.println("2. 生成的类包含这些变量作为final字段");
        System.out.println("3. Bootstrap方法传递捕获的值");
        
        // 手动实现闭包捕获
        demonstrateClosureCapture(prefix, multiplier);
        System.out.println();
    }
    
    public static void demonstrateClosureCapture(String prefix, int multiplier) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        
        // 获取捕获变量的实现方法
        MethodHandle implementation = lookup.findStatic(LambdaCallSiteExample.class,
            "closureImplementation", 
            MethodType.methodType(String.class, String.class, int.class, Integer.class));
        
        // 绑定捕获的变量
        MethodHandle boundImpl = MethodHandles.insertArguments(implementation, 0, prefix, multiplier);
        
        // 创建lambda
        CallSite callSite = LambdaMetafactory.metafactory(
            lookup,
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            boundImpl,
            MethodType.methodType(String.class, Integer.class)
        );
        
        Function<Integer, String> lambda = (Function<Integer, String>) callSite.dynamicInvoker().invoke();
        System.out.println("手动闭包实现: " + lambda.apply(7));
    }
    
    public static String closureImplementation(String prefix, int multiplier, Integer x) {
        return prefix + (x * multiplier);
    }
    
    // 6. Method Reference的实现
    public static void methodReferenceExample() throws Throwable {
        System.out.println("6. Method Reference实现:");
        
        // 静态方法引用
        Function<String, Integer> staticRef = Integer::parseInt;
        System.out.println("静态方法引用: " + staticRef.apply("123"));
        
        // 实例方法引用
        String str = "hello world";
        Supplier<String> instanceRef = str::toUpperCase;
        System.out.println("实例方法引用: " + instanceRef.get());
        
        // 构造函数引用
        Function<String, StringBuilder> constructorRef = StringBuilder::new;
        StringBuilder sb = constructorRef.apply("initial");
        System.out.println("构造函数引用: " + sb.toString());
        
        System.out.println("\nMethod Reference的字节码特点:");
        System.out.println("1. 直接引用已存在的方法，无需生成新的合成方法");
        System.out.println("2. Bootstrap参数中直接传递目标方法的MethodHandle");
        System.out.println("3. 性能通常比lambda表达式更好");
        
        // 演示method reference的性能优势
        demonstrateMethodReferencePerformance();
        System.out.println();
    }
    
    public static void demonstrateMethodReferencePerformance() {
        int iterations = 5_000_000;
        List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");
        
        // Method Reference
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            numbers.stream().mapToInt(Integer::parseInt).sum();
        }
        long methodRefTime = System.nanoTime() - startTime;
        
        // Lambda表达式
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            numbers.stream().mapToInt(s -> Integer.parseInt(s)).sum();
        }
        long lambdaTime = System.nanoTime() - startTime;
        
        System.out.println("Method Reference耗时: " + methodRefTime / 1_000_000 + " ms");
        System.out.println("Lambda表达式耗时: " + lambdaTime / 1_000_000 + " ms");
        System.out.println("性能提升: " + String.format("%.2f", lambdaTime / (double) methodRefTime) + "x");
    }
}

// 详细的字节码分析类
class LambdaBytecodeAnalysis {
    
    public static void analyzeLambdaBytecode() {
        System.out.println("=== Lambda字节码详细分析 ===");
        
        explainSimpleLambdaBytecode();
        explainClosureLambdaBytecode();
        explainMethodReferenceBytecode();
    }
    
    public static void explainSimpleLambdaBytecode() {
        System.out.println("\n1. 简单Lambda的字节码:");
        System.out.println("源代码: Function<String, String> f = s -> s.toUpperCase();");
        System.out.println();
        
        System.out.println("编译后的字节码:");
        System.out.println("0: invokedynamic #2,  0  // InvokeDynamic #0:apply:()Ljava/util/function/Function;");
        System.out.println("5: astore_1");
        System.out.println();
        
        System.out.println("Bootstrap Methods:");
        System.out.println("0: #18 REF_invokeStatic java/lang/invoke/LambdaMetafactory.metafactory:");
        System.out.println("   Method arguments:");
        System.out.println("   #19 (Ljava/lang/Object;)Ljava/lang/Object;  // 接口方法签名");
        System.out.println("   #20 REF_invokeStatic lambda$main$0:(Ljava/lang/String;)Ljava/lang/String;  // 实现方法");
        System.out.println("   #19 (Ljava/lang/String;)Ljava/lang/String;  // 实际方法签名");
        System.out.println();
        
        System.out.println("生成的合成方法:");
        System.out.println("private static java.lang.String lambda$main$0(java.lang.String);");
        System.out.println("  Code:");
        System.out.println("   0: aload_0");
        System.out.println("   1: invokevirtual #21  // Method java/lang/String.toUpperCase:()Ljava/lang/String;");
        System.out.println("   4: areturn");
    }
    
    public static void explainClosureLambdaBytecode() {
        System.out.println("\n2. 闭包Lambda的字节码:");
        System.out.println("源代码: ");
        System.out.println("String prefix = \"Result: \";");
        System.out.println("Function<String, String> f = s -> prefix + s;");
        System.out.println();
        
        System.out.println("编译后的字节码:");
        System.out.println("3: ldc           #3      // String \"Result: \"");
        System.out.println("5: astore_2");
        System.out.println("6: aload_2              // 加载捕获的变量");
        System.out.println("7: invokedynamic #4,  0 // InvokeDynamic #1:apply:(Ljava/lang/String;)Ljava/util/function/Function;");
        System.out.println("12: astore_3");
        System.out.println();
        
        System.out.println("Bootstrap方法参数中包含捕获的值:");
        System.out.println("- factory type变为 (Ljava/lang/String;)Ljava/util/function/Function;");
        System.out.println("- 实现方法接收额外的捕获参数");
    }
    
    public static void explainMethodReferenceBytecode() {
        System.out.println("\n3. Method Reference的字节码:");
        System.out.println("源代码: Function<String, Integer> f = Integer::parseInt;");
        System.out.println();
        
        System.out.println("编译后的字节码:");
        System.out.println("0: invokedynamic #2,  0  // InvokeDynamic #0:apply:()Ljava/util/function/Function;");
        System.out.println("5: astore_1");
        System.out.println();
        
        System.out.println("Bootstrap方法参数:");
        System.out.println("- 直接引用 Integer.parseInt 方法");
        System.out.println("- 不生成额外的合成方法");
        System.out.println("- REF_invokeStatic java/lang/Integer.parseInt:(Ljava/lang/String;)I");
    }
}

// Lambda实现的底层机制说明
class LambdaImplementationMechanism {
    
    public static void explainLambdaMechanism() {
        System.out.println("=== Lambda实现机制详解 ===");
        
        System.out.println("\n1. 编译期处理:");
        System.out.println("- 编译器分析lambda表达式");
        System.out.println("- 生成私有的静态合成方法");
        System.out.println("- 将lambda体的代码移到合成方法中");
        System.out.println("- 生成invokedynamic指令替换lambda表达式");
        
        System.out.println("\n2. 运行期处理:");
        System.out.println("- 首次执行invokedynamic时调用bootstrap方法");
        System.out.println("- LambdaMetafactory动态生成实现类");
        System.out.println("- 返回CallSite，包含工厂方法的MethodHandle");
        System.out.println("- 后续调用重用生成的类和CallSite");
        
        System.out.println("\n3. 生成的类结构:");
        System.out.println("- 实现目标函数式接口");
        System.out.println("- 包含捕获变量的final字段");
        System.out.println("- 构造函数初始化捕获的变量");
        System.out.println("- 接口方法调用原始的合成方法");
        
        System.out.println("\n4. 性能优化:");
        System.out.println("- 避免匿名内部类的类文件生成");
        System.out.println("- JIT编译器可以积极优化");
        System.out.println("- 内联消除和逃逸分析");
        System.out.println("- 特化避免装箱/拆箱开销");
    }
}

/*
Lambda CallSite 关键要点总结:

1. 编译转换:
   - Lambda表达式 → invokedynamic指令
   - Lambda体 → 私有静态合成方法
   - 捕获变量 → 方法参数或构造函数参数

2. Bootstrap过程:
   - LambdaMetafactory.metafactory作为bootstrap方法
   - 动态生成实现函数式接口的类
   - 返回包含工厂方法的CallSite

3. 运行时优化:
   - CallSite缓存避免重复类生成
   - JIT编译器内联优化
   - 特化消除装箱开销

4. 性能特征:
   - 首次调用有动态生成开销
   - 后续调用性能接近直接方法调用
   - Method Reference通常比Lambda表达式更快
   - 闭包捕获增加轻微开销

5. 字节码特点:
   - invokedynamic指令替换lambda表达式
   - Bootstrap Methods表记录元数据
   - 合成方法包含实际的lambda逻辑

这种设计使得Lambda既保持了语法的简洁性，
又通过invokedynamic和CallSite机制实现了高效的运行时性能。
*/