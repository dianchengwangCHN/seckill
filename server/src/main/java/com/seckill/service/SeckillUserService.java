package com.seckill.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seckill.dao.SeckillUserDao;
import com.seckill.domain.SeckillUser;
import com.seckill.enums.CodeMsg;
import com.seckill.exception.SeckillException;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillUserKey;
import com.seckill.util.MD5Util;
import com.seckill.util.UUIDUtil;
import com.seckill.vo.AuthVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SeckillUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    SeckillUserDao seckillUserDao;

    @Autowired
    RedisService redisService;

    public SeckillUser getByEmail(String email) {
        SeckillUser user = null;
        try {
            user = redisService.get(SeckillUserKey.getByEmail, "" + email, SeckillUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user != null) {
            return user;
        }
        user = seckillUserDao.getByEmail(email);
        if (user != null) {
            try {
                redisService.set(SeckillUserKey.getByEmail, "" + email, user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser user = null;
        try {
            user = redisService.get(SeckillUserKey.getToken, token, SeckillUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public boolean updatePassword(String token, String email, String formPass) {
        SeckillUser user = getByEmail(email);
        if (user == null) {
            System.out.println("user does not exists");
        }
        SeckillUser toBeUpdate = new SeckillUser();
        toBeUpdate.setEmail(email);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        seckillUserDao.update(toBeUpdate);
        // update cache
        redisService.delete(SeckillUserKey.getByEmail, "" + email);
        user.setPassword(toBeUpdate.getPassword());
        try {
            redisService.set(SeckillUserKey.getToken, token, user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String login(HttpServletResponse response, AuthVo authVo) {
        if (authVo == null) {
            throw new SeckillException(CodeMsg.SERVER_ERROR);
        }
        String email = authVo.getEmail();
        String formPass = authVo.getPassword();
        SeckillUser user = getByEmail(email);
        if (user == null) {
            throw new SeckillException(CodeMsg.SECKILL_FAIL);
        }
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new SeckillException(CodeMsg.SECKILL_FAIL);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        try {
            redisService.set(SeckillUserKey.getToken, token, user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.getToken.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

