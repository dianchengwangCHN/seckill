package com.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "hfldaj124703d90u51";

    public static String inputPassToFormPass(String inputPass) {
        String str = salt.substring(0, salt.length() / 2) + inputPass + salt.substring(salt.length() / 2, salt.length());
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt.substring(0, salt.length() / 2) + formPass + salt.substring(salt.length() / 2, salt.length());
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
}
