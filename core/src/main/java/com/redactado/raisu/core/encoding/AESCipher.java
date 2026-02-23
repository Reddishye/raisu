package com.redactado.raisu.core.encoding;

import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.jetbrains.annotations.NotNull;

public final class AESCipher {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 16;
    private static final int IV_SIZE = 16;

    public byte @NotNull [] generateKey() {
        byte[] key = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(key);
        return key;
    }

    public byte @NotNull [] encrypt(byte @NotNull [] data, byte @NotNull [] key) {
        try {
            byte[] iv = new byte[IV_SIZE];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

            byte[] encrypted = cipher.doFinal(data);
            byte[] result = new byte[IV_SIZE + encrypted.length];
            System.arraycopy(iv, 0, result, 0, IV_SIZE);
            System.arraycopy(encrypted, 0, result, IV_SIZE, encrypted.length);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public byte @NotNull [] decrypt(byte @NotNull [] data, byte @NotNull [] key) {
        try {
            byte[] iv = Arrays.copyOfRange(data, 0, IV_SIZE);
            byte[] ciphertext = Arrays.copyOfRange(data, IV_SIZE, data.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
