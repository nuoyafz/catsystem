package com.catfeeding.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 密码工具类
 * @author 方舟
 */
public class PasswordUtils {

    /**
     * MD5加密
     */
    public static String encrypt(String password) {
        return DigestUtil.md5Hex(password);
    }

    /**
     * 验证密码
     */
    public static boolean verify(String rawPassword, String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }
}
