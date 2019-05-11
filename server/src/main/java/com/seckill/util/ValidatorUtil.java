package com.seckill.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        return email_pattern.matcher(email).matches();
    }
}
