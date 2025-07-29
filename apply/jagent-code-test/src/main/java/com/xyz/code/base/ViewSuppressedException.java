package com.xyz.code.base;

public class ViewSuppressedException {

    public static void main(String[] args) {
        try {
            // 调用会产生抑制异常的方法
            myMethod();
        } catch (Exception e) {
            System.out.println("Caught the final thrown exception:");
            System.out.println("  => " + e); // 打印最终的异常
            System.out.println("--------------------------------------------------");

            if (e.getSuppressed().length > 0) {
                System.out.println("Suppressed exceptions found:");
                for (Throwable suppressed : e.getSuppressed()) {
                    System.out.println("  => " + suppressed.toString()); // 打印被"吞掉"的异常
                }
            } else {
                System.out.println("No suppressed exceptions found.");
            }
        }
    }

    public static void myMethod() throws Exception {
        try {
            throw new Exception("Exception from myMethod");
        }  finally {
            // finally 块抛出了一个异常
            throw new Exception("Exception from finally");
        }
    }
}