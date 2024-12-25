package com.lqx.plugins.collect;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public final class TupleTrack{
    public  String first;
    public  StackTraceElement[] second;

    private TupleTrack(String first, StackTraceElement[] second) {
        this.first = first;
        this.second = second;
    }

    public TupleTrack() {
    }

}
