package com.rhplateforme.token;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private static final Map<String, String> tokenMap = new HashMap<>();

    public static String generateToken(String email) {
        String token = TokenGenerator.generateToken();
        tokenMap.put(email, token);
        return token;
    }

    public static String getToken(String email) {
        return tokenMap.get(email);
    }

    public static void removeToken(String email) {
        tokenMap.remove(email);
    }
}
