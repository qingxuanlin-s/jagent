package com.lqx.other;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ImapFun1 implements Function<String, Map<String, AtomicInteger>> {

    @Override
    public Map<String, AtomicInteger> apply(String s) {
        return new ConcurrentHashMap<>();
    }
}
