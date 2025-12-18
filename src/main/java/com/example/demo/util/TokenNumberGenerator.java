package com.example.demo.util;

import java.util.UUID;

public class TokenNumberGenerator {

    // Simple static method to generate a unique token number
    public static String generateTokenNumber() {
        // Example: random 8-character string
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}