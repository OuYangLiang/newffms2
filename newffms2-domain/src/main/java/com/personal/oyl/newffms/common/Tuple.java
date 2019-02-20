package com.personal.oyl.newffms.common;

public class Tuple<A, B> {
    private final A first;
    private final B second;

    public Tuple(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
