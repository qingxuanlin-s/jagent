package com.lqx.plugins.collect;

import com.lqx.other.ImapFun1;
import com.lqx.other.ImapFun2;
import com.lqx.plugins.Tuple2;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class StackRunner implements Runnable {

    private TupleTrack tupleTrack ;

    private static String COM_KEY = "xyz";


    public StackRunner() {
        super();
    }

    private static Map<String, Map<String,  AtomicInteger>> multiLevelMap = new ConcurrentHashMap<>();


    public StackRunner(TupleTrack tupleTrack) {
        this.tupleTrack = tupleTrack;
    }

    public static Tuple2<String, Map<String,AtomicInteger>> getStat(String method){
        return Tuple2.with(method, multiLevelMap.get(method));
    }

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        int x = 1;
        StringBuilder stacks = new StringBuilder();
        for (int i = 0; i < tupleTrack.second.length; i++) {
            StackTraceElement element = tupleTrack.second[i];
            if(element.getClassName().contains(COM_KEY)){
                StringBuilder tmp = new StringBuilder();
                for (int z = 0; z < x; z++) {
                    tmp.append("-");
                }
                stacks.append(tmp).append(">").append(" ").append(element.getClassName()).append("#").append(element.getMethodName()).append("@").append(element.getLineNumber()).append("\n");
                x ++;
            }
        }


        String cname = tupleTrack.first;

        AtomicInteger computeIfAbsent = multiLevelMap
                .computeIfAbsent(cname, new ImapFun1())
                .computeIfAbsent(stacks.toString(), new ImapFun2());
        computeIfAbsent.incrementAndGet();

        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }
}
