package com.usercrud.demo.service;

import com.usercrud.demo.filters.RSAKeyloader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class RSAService {
    private final RSAKeyloader rsaKeyLoader;

    public String decryptData(String encryptedPassword) throws Exception {
        log.info("Received encrypted data: {}", encryptedPassword);

        try {
            String cleanedInput = encryptedPassword.trim();
            if (!cleanedInput.matches("^[A-Za-z0-9+/=]+$")) {
                throw new IllegalArgumentException("Input is not valid Base64!");
            }
            byte[] encryptedBytes = Base64.getDecoder().decode(cleanedInput);
            return decrypt(encryptedBytes, rsaKeyLoader.getPrivateKey());
        } catch (Exception e) {
            log.info("decrypt-> Unable to decrypt the data: {}", e.getMessage());
            return null;
        }

    }

    private String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(data));
    }

}
