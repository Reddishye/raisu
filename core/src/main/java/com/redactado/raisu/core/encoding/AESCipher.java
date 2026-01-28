package com.redactado.raisu.core.encoding;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.jetbrains.annotations.NotNull;

public final class AESCipher {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    @NotNull
    public byte[] encrypt(@NotNull byte[] data, @NotNull String password) {
        try {
            byte[] key = deriveKey(password);
            byte[] iv = generateIV();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

            byte[] encrypted = cipher.doFinal(data);
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    @NotNull
    public byte[] decrypt(@NotNull byte[] data, @NotNull String password) {
        try {
            byte[] key = deriveKey(password);
            byte[] iv = Arrays.copyOfRange(data, 0, IV_SIZE);
            byte[] encrypted = Arrays.copyOfRange(data, IV_SIZE, data.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    @NotNull
    private byte[] deriveKey(@NotNull String password) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(password.getBytes(StandardCharsets.UTF_8));
            return Arrays.copyOf(key, 16);
        } catch (Exception e) {
            throw new RuntimeException("Key derivation failed", e);
        }
    }

    @NotNull
    private byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
