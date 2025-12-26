package com.example.demo.util;

import java.util.concurrent.atomic.AtomicLong;

public class TokenNumberGenerator {

    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    // Generates a unique token number
    public static String generateTokenNumber() {
        return "T" + counter.getAndIncrement();
    }
}
