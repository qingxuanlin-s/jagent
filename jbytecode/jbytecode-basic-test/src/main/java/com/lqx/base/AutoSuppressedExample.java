package com.lqx.base;

// 一个会在工作和关闭时都抛出异常的“坏”资源
class BadResource implements AutoCloseable {
    public void work() {
        throw new RuntimeException("工作时发生错误 (Error during work)!");
    }
    @Override
    public void close() {
        throw new RuntimeException("关闭时发生错误 (Error during close)!");
    }
}

public class AutoSuppressedExample {
    public static void main(String[] args) {
        try (BadResource br = new BadResource()) {
            br.work();
        } catch (Exception e) {
            System.out.println("捕获到的主异常: " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("被抑制的异常: " + suppressed.getMessage());
            }
        }
    }
}