package com.lqx;

import java.lang.instrument.Instrumentation;

public class PreMainAgent {

    /**
     * jvm 参数：
     * -javaagent:/Users/..../target/java-agent-1.0-SNAPSHOT-jar-with-dependencies.jar
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
    }

}