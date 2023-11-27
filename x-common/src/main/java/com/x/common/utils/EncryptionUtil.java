package com.x.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author : xuemingqi
 * @since : 2023/1/14 18:01
 */
@Slf4j
public class EncryptionUtil {

    /**
     * 默认公钥
     */
    private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaQ/Hsk2BUBgRoVa2Tfqrhrwr75240jHohTdInrBAYcUiHjHAq5Al06yekrLzreCJmg+W8faoKOc7AEVJ8av26ZwQr0gI+eJSMUGRynCP+K1CaDc7BkBvI/ZvlMtdqhuPcCKsD02cINyAXuyHGmTj+Q6gxO4IMO+QuAGMoxzO9LwIDAQAB";

    /**
     * 默认私钥
     */
    private static final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJpD8eyTYFQGBGhVrZN+quGvCvvnbjSMeiFN0iesEBhxSIeMcCrkCXTrJ6SsvOt4ImaD5bx9qgo5zsARUnxq/bpnBCvSAj54lIxQZHKcI/4rUJoNzsGQG8j9m+Uy12qG49wIqwPTZwg3IBe7IcaZOP5DqDE7ggw75C4AYyjHM70vAgMBAAECgYARcrLnu6mPB1kzRdGKl66TNR2sbTopRmQ/5RHqe8scQJ/f+PHs3/rz1u8WcJUzmtRlPPDcl2gGnRA16B+ow98rq4PFFgQf+IvMk2LFHpAUNuG/wfyea6+DeTo1saNws+xA3XqfBOcqYwm85phwcFNeWjmj8ogVi5Skim9neCZNcQJBANgJUiDXLRR1nws1LEfUqfv/70ggGa3emLYBDpGQuZbpuksMqfskpvuUdMjR1xz58ovFpvBFUlbImecEbrLUbhECQQC2zWFBFz6cRHaqlqQ9O5lcv7JCJ7nQl+AUBMOJY8JHgr9N6Vi0SI1CYRtzBGLhDAeSGj3Bb7vJ69P0Nf6h1zc/AkAnENBsJdz+BSMEeNIQDzHJ4wcXCM779POyfdBvnDcjRhKs/ZZDVuZnOfpAUknO8JfelJhSfIaeMNo7vz5xUh6xAkAHoD3EZ2HXRwvkRfCIFWpqchzPobSgvRU+AIzfuGescAnnCCsJKlWqRT7N54rwdH4EXE/jN/QPkmT/NRlwSvglAkBiS5WfQr/Xt3V6tbA2TLQtPFovV3XXNcr/3KevUABQDRWj5Fy5S5VSQCknq6O9LC+DJxsCOj3EvfWAWe56+37S";

    /**
     * sha256
     *
     * @param data 加密内容
     * @param key  盐
     */
    public static String hmacSha256(String data, String key) {
        String encodeStr = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            encodeStr = Base64.encodeBase64String(hash);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return encodeStr;
    }

    /**
     * rsa加密
     *
     * @param str 待加密字符串
     */
    public static String rsaEncrypt(String str) {
        return rsaEncrypt(str, PUBLICKEY);
    }

    /**
     * rsa加密
     *
     * @param str       待加密字符串
     * @param publicKey 公钥
     */
    public static String rsaEncrypt(String str, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        //公钥加密
        byte[] encrypt = rsa.encrypt(StrUtil.utf8Bytes(str), KeyType.PublicKey);
        return StrUtil.utf8Str(cn.hutool.core.codec.Base64.encode(encrypt));
    }

    /**
     * rsa解密
     *
     * @param str 待解密字符串
     */
    public static String rsaDecrypt(String str) {
        return rsaDecrypt(str, PRIVATEKEY);
    }

    /**
     * rsa解密
     *
     * @param str        待解密字符串
     * @param privateKey 私钥
     */
    public static String rsaDecrypt(String str, String privateKey) {
        RSA rsa2 = new RSA(privateKey, null);
        byte[] decrypt = rsa2.decrypt(str, KeyType.PrivateKey);
        return StrUtil.utf8Str(decrypt);
    }

}
