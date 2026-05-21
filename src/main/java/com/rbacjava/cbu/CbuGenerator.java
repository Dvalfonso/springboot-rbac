package com.rbacjava.cbu;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CbuGenerator {
    private static final AtomicLong SEQUENCE = new AtomicLong(1L);

    private CbuGenerator() {}

    public static String generate() {
        long seq = SEQUENCE.getAndIncrement();
        // 22 dígitos: prefijo fijo de dev (8) + secuencia paddeada (14)
        return String.format("07200001%014d", seq);
    }
}
