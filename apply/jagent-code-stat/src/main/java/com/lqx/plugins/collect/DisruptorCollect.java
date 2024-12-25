package com.lqx.plugins.collect;


import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.lqx.CodeAttachUtil.dyncClassLoader;

public class DisruptorCollect {
    public static final LinkedBlockingQueue<Runnable> RUNNABLES = new LinkedBlockingQueue<>();

    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(0, 5,
            0L, TimeUnit.MILLISECONDS, RUNNABLES);


    static {
        try {
            dyncClassLoader.loadClass("com.lqx.plugins.collect.StackRunner");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

        StackRunner stackRunner = (StackRunner) (dyncClassLoader.loadClass("com.lqx.plugins.collect.StackRunner").newInstance());
        stackRunner.setTupleTrack(tupleTrack);

        EXECUTOR.submit(stackRunner);

    }

}
