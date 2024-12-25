

package com.lqx.plugins;

import lombok.ToString;

@ToString
public final class Tuple2<A, B> {
    public final A first;
    public final B second;

    private Tuple2(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Tuple2<A, B> with(A first, B second) {
        return new Tuple2(first, second);
    }


}
