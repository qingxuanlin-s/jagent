package com.lqx.other;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ImapFun2 implements Function<String, AtomicInteger> {

    @Override
    public AtomicInteger apply(String s) {
        return new AtomicInteger(0);
    }
}
