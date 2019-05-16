package com.seckill.vo;

import com.seckill.validator.IsEmail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthVo {

    @NotNull
    @IsEmail
    private String email;

    @NotNull
    @Length(min=32)
    private String password;

    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthVo [email=" + email + ", password=" + password + "]";
    }
}
