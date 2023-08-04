package com.moing.backend.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AesUtil {
    private final SecretKeySpec secretKeySpec;
    private final IvParameterSpec ivParameterSpec;

    public AesUtil(@Value("${aes.secret.key}") String key, @Value("${aes.secret.alg}") String alg) {
        this.secretKeySpec = createSecretKeySpec(key, alg);
        this.ivParameterSpec = createIvParameterSpec(key);
    }

    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(secretKeySpec.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt text: " + e.getMessage(), e);
        }
    }

    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(secretKeySpec.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt cipherText: " + e.getMessage(), e);
        }
    }

    private SecretKeySpec createSecretKeySpec(String key, String alg) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(keyBytes);
            return new SecretKeySpec(hash, alg);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to create SecretKeySpec: " + e.getMessage(), e);
        }
    }

    private IvParameterSpec createIvParameterSpec(String key) {
        byte[] iv = key.substring(0, 16).getBytes(StandardCharsets.UTF_8);
        return new IvParameterSpec(iv);
    }
}

