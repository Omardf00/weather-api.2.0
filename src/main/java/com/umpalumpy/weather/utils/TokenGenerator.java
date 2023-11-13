package com.umpalumpy.weather.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class TokenGenerator {
    public static void main(String[] args) {
        try {
            // Generate a 256-bit (32-byte) key using SecureRandom
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // Key size is set to 256 bits
            Key key = keyGenerator.generateKey();

            // Get the key bytes
            byte[] keyBytes = key.getEncoded();

            // Print the key bytes (in hexadecimal)
            System.out.println("Generated Key: " + bytesToHex(keyBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

}
