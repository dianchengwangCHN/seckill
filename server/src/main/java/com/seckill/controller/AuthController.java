package com.seckill.controller;

import com.seckill.redis.RedisService;
import com.seckill.service.SeckillUserService;
import com.seckill.vo.AuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.transform.Result;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @PostMapping(value = "/login", produces = "application/json")
    public String login(HttpServletResponse response, @Valid AuthVo authVo) {
        log.info(authVo.toString());
        String token = "";
        return token;
    }
}
