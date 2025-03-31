package com.usercrud.demo.controller;

import com.usercrud.demo.service.RSAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rsa")
public class RSAController {

    private final RSAService rsaService;

    @PostMapping("/decrypt")
    public String decryptMessage(@RequestBody Map<String, String> requestBody) {
        String encryptedMessage = requestBody.get("encryptedMessage");
        log.info("Received encrypted message: {}", encryptedMessage);
        try {
            return rsaService.decryptData(encryptedMessage);
        } catch (Exception e) {
            return "Decryption failed: " + e.getMessage();
        }
    }

}
