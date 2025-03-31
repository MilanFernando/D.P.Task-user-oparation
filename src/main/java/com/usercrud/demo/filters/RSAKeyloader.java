package com.usercrud.demo.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class RSAKeyloader {

    @Value("${rsa.private.key}")
    public String privateKeyString;

    public PrivateKey getPrivateKey() {
        try {
            // Remove any spaces or newline characters
            String cleanedKey = privateKeyString.replaceAll("\\s", "");

            // Decode Base64-encoded private key
            byte[] keyBytes= Base64.getDecoder().decode(cleanedKey);

            // Generate PrivateKey from decoded bytes
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key: " + e.getMessage(), e);
        }

    }
}
