package com.lltech.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author Created by L.C.Y on 2018-11-26
 */
public class Md5Util {

    private Md5Util(){}

    /**
     * 生成MD5
     * @param initValue 初始值
     * @return MD5加密后的值
     */
    public static String generateMD5(String initValue) {
        return new Md5Hash(initValue).toString();
    }

    /**
     * 校验初始值与MD5是否相等
     * @param md5 加密后的MD5
     * @param initValue 初始值
     * @return boolean
     */
    public static boolean validateMD5(String md5, String initValue) {
        return md5.equals(generateMD5(initValue));
    }

    /**
     * 生成加盐MD5
     * @param initValue 初始值
     * @return 加盐后的MD5
     */
    public static String generateSaltMD5(String initValue, String salt) {
        return new Md5Hash(initValue, salt).toString();
    }

    /**
     * 校验初始值与加盐MD5是否相等
     * @param saltMD5 加盐后的MD5
     * @param initValue 初始值
     * @return boolean
     */
    public static boolean validateSaltMD5(String saltMD5, String initValue, String salt) {
        return saltMD5.equals(generateSaltMD5(initValue, salt));
    }
}
