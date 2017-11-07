package ru.ir.steam;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {

    public static PublicKey decodePublicKey(String encodedKey) throws Exception {
        return decodePublicKey(Base64.getDecoder().decode(encodedKey));
    }

    public static PublicKey decodePublicKey(byte[] encodedKey) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedKey);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    public static byte[] encrypt(PublicKey key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static String encryptB64(PublicKey key, byte[] data) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(key, data));
    }

    public static String encryptB64(PublicKey key, String data) throws Exception {
        return encryptB64(key, data.getBytes(StandardCharsets.US_ASCII));
    }

}
