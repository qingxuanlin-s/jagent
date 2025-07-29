package com.lqx.base.invoke.invokestatic;

public class ClosureCaptureExample {

    public static void demonstrateCapture() {
        String prefix = "Log Entry: "; // 这是一个局部变量，它是“事实上的final”
        int counter = 0; // 这也是“事实上的final”

        // 当这个Lambda被创建时，它“捕获”了 prefix 和 counter 的值。
        Runnable task = () -> {
            // 在这里，Lambda可以使用被捕获的变量
            System.out.println(prefix + "Task executed " + counter + " times.");
        };

        // 如果你试图在Lambda创建后修改被捕获的变量，编译器会报错！
        // prefix = "New Prefix: "; // 取消此行注释会导致编译错误！
        // counter++;             // 取消此行注释也会导致编译错误！

        // 将任务传递到另一个线程中执行
        new Thread(task).start();
    }

    public static void main(String[] args) {
        demonstrateCapture();
    }
}