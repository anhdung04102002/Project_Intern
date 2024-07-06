package com.example.jwtspringsecurity.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class SecretGenerator {

    public static void main(String[] args) {
        // Generate a secure random secret key with 256 bits length
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Encode the secret key to Base64 string
        String secret = Encoders.BASE64.encode(secretKey.getEncoded());

        // Print out the generated secret
        System.out.println("Generated Secret: " + secret);
    }
}
