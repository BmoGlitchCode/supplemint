package com.BmoGlitchCode.supplemint.adapter.output.security.user;

import com.BmoGlitchCode.supplemint.domain.port.output.user.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Simple password encoder implementation using SHA-256 with salt.
 * For production, consider using Spring Security's BCryptPasswordEncoder.
 */
@Component
public class SimplePasswordEncoder implements PasswordEncoder {

    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = ":";

    @Override
    public String encode(String rawPassword) {
        byte[] salt = generateSalt();
        byte[] hash = hash(rawPassword, salt);

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        return saltBase64 + DELIMITER + hashBase64;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split(DELIMITER);
        if (parts.length != 2) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[1]);
        byte[] actualHash = hash(rawPassword, salt);

        return MessageDigest.isEqual(expectedHash, actualHash);
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] hash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
