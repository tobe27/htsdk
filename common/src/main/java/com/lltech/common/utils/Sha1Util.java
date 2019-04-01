package com.lltech.common.utils;

import org.apache.shiro.crypto.hash.Sha1Hash;

/**
 * @author CREATED BY L.C.Y on 2019-4-1
 */
public class Sha1Util {
    private Sha1Util(){}

    /**
     * sha1加密
     * @param initValue 待加密字符串
     * @return 加密后的字符串
     */
    public static String generateSha1(String initValue) {
        return new Sha1Hash(initValue).toString();
    }

    /**
     * 校验sha1加密
     * @param sha1 已加密字符串
     * @param initValue 待加密字符串
     * @return true- 校验通过
     *         false- 校验不通过
     */
    public static boolean validateSha1(String sha1, String initValue) {
        return sha1.equals(generateSha1(initValue));
    }

    /**
     * 生成sha1加盐加密
     * @param initValue 待加密字符串
     * @param salt 加的盐
     * @return 加盐加密后的字符串
     */
    public static String generateSaltSha1(String initValue, String salt) {
        return new Sha1Hash(initValue, salt).toString();
    }

    /**
     * 校验sha1加盐加密
     * @param saltSha1 已加盐加密字符串
     * @param initValue 待加密字符串
     * @param salt 加的盐
     * @return true- 校验通过
     *         false- 校验不通过
     */
    public static boolean validateSaltSha1(String saltSha1, String initValue, String salt) {
        return saltSha1.equals(generateSaltSha1(initValue, salt));
    }

}
