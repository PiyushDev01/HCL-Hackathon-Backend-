package com.hackathon.app.util;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class JwtUtil {

    // Simple mock JWT for hackathon purposes
    public String generateToken(String email) {
        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String payload = Base64.getEncoder().encodeToString(("{\"sub\":\"" + email + "\"}").getBytes());
        String signature = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        return header + "." + payload + "." + signature;
    }

    public boolean validateToken(String token) {
        return token != null && token.split("\\.").length == 3;
    }
}
