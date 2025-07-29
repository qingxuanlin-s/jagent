package com.lqx.base;

public class TestUtf8 {
    public static void main(String[] args) {
        char c = '是';//可以被编译
        //char c2 = '😀'; // 不能被编译

        char c1 = '\uD83D';
        char c2 = '\uDE00';
        System.out.println(c1+""+c2);//输出是 😀


        String emoji = "😀";
        System.out.println(emoji.length()); // 输出是 2, 而不是 1

        // 如何获取 emoji 的 codePoint
        char high = emoji.charAt(0); // high 的值是 '\uD83D'
        char low = emoji.charAt(1);  // low 的值是 '\uDE00'
        int codePoint = (high - 0xD800) * 0x400 + (low - 0xDC00) + 0x10000; // codePoint 的值是 128512
        emoji.codePoints().forEach(System.out::println); // 输出是 128512
        System.out.println(codePoint); // 输出是 128512
    }
}
