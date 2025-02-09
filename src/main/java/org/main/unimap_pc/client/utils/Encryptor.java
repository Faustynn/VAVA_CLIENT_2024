package org.main.unimap_pc.client.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.main.unimap_pc.client.configs.AppConfig;

public class Encryptor {
    private static final String ALGORITHM = AppConfig.getAesAlgorithm();
    private static final String SECRET_KEY = AppConfig.getSecretKey();
    private static final String IV = AppConfig.getIv();

    public static String encrypt(String data) throws Exception {
        if (ALGORITHM == null || ALGORITHM.isEmpty()) {
            throw new IllegalArgumentException("Algorithm is null or empty");
        }

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
