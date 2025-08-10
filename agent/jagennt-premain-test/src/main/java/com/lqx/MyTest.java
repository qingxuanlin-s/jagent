package com.lqx;

/**
 * VM-OPTION(虚拟机选项):
 * -javaagent:/Users/mrfox/IdeaProjects/jagent-new/agent/jagent-premain/target/jagent-premain-1.0-SNAPSHOT-jar-with-dependencies.jar
 *
 * */
public class MyTest {
    public static void main(String[] args) {
        System.out.println(foo());
    }


    public static int foo(){
        return 100;
    }
}