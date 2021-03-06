package com.example.dna.smsresolver;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by dna on 7/21/16.
 */
public class StringCryptor {

    private static final String CIPHER_ALGORITHM = "AES";
    private static final String RANDOM_GENERATOR_ALGORITHM = "SHA1PRNG";
    private static final int RANDOM_KEY_SIZE = 128;

    public StringCryptor() {
    }

    public static String encrypt(String password, String data) throws Exception {
        byte[] secretKey = generateKey(password.getBytes());
        byte[] clear = data.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secretKeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        String encryptedString = Base64.encodeToString(encrypted, 0);
        return encryptedString;
    }

    public static String decrypt(String password, String encryptedData) throws Exception {
        byte[] secretKey = generateKey(password.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKeySpec);
        byte[] encrypted = Base64.decode(encryptedData, 0);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    public static byte[] generateKey(byte[] seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed);
        keyGenerator.init(128, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
}
