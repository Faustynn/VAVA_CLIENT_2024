package org.main.unimap_pc.client.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import de.jensd.fx.glyphs.testapps.App;
import org.main.unimap_pc.client.configs.AppConfig;

public class Encryptor {

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AppConfig.getAesAlgorithm());
        SecretKeySpec secretKeySpec = new SecretKeySpec(AppConfig.getSecretKey().getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AppConfig.getIv().getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}
