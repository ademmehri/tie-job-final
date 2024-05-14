package com.rhplateforme.token;
import java.security.SecureRandom;

public class TokenGenerator {
    private static final String numbers = "1234567890";
    private static final int TOKEN_LENGTH = 6;

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(numbers.length());
            token.append(numbers.charAt(randomIndex));
        }

        return token.toString();
    }
}