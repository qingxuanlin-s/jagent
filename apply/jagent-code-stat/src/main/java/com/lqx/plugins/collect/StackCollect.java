package com.lqx.plugins.collect;


import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.lqx.CodeAttachUtil.dyncClassLoader;

public class StackCollect {
    public static final LinkedBlockingQueue<Runnable> RUNNABLES = new LinkedBlockingQueue<>();

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(0, 5,
            0L, TimeUnit.MILLISECONDS, RUNNABLES);


    private static final String CLAZZ_NAME = "com.lqx.plugins.collect.StackRunner";

    static {
        dyncClassLoader.loadClass(CLAZZ_NAME,true);
    }

    @SneakyThrows
    public static void putStacks(String cname, StackTraceElement[] stackTrace){
        if(RUNNABLES.size() > 1000){
            System.out.println("消费能力不足");
        }

        //用上面的索引取出一个空的事件用于填充
        TupleTrack tupleTrack = new TupleTrack();
        tupleTrack.setFirst(cname);
        tupleTrack.setSecond(stackTrace);

        StackRunner stackRunner = (StackRunner) (dyncClassLoader.findClass(CLAZZ_NAME).newInstance());
        stackRunner.setTupleTrack(tupleTrack);
        stackRunner.run();

        //EXECUTOR.submit(stackRunner);

    }

}
