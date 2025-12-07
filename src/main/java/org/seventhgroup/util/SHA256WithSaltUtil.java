package org.seventhgroup.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256WithSaltUtil {

    // 盐值长度：16字节
    private static final int SALT_LENGTH = 16;

    //生成16字节的随机盐值
    public static byte[] generate16ByteSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    //SHA-256加盐加密（此时盐为字节数组）
    public static String encryptWith16ByteSalt(String content, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] digest = md.digest(contentBytes);
        return bytesToHex(digest);
    }
    //盐为Base64字符串，哈希值为十六进制字符串
    public static boolean verify(String password, String storedSalt, String storedHash) {
        try {
            byte[] salt = Base64.getDecoder().decode(storedSalt);
            String inputHash = encryptWith16ByteSalt(password, salt);
            return MessageDigest.isEqual(inputHash.getBytes(), storedHash.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //字节数组转16进制字符串（用于哈希值）
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexStr.append('0');
            }
            hexStr.append(hex);
        }
        return hexStr.toString();
    }

    //字节数组转Base64字符串（用于盐）
    public static String bytesToBase64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        String base64String = encoder.encodeToString(bytes);
        return base64String;
    }
}