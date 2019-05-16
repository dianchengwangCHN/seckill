package com.seckill.controller;

import com.seckill.dto.Result;
import com.seckill.redis.RedisService;
import com.seckill.service.SeckillUserService;
import com.seckill.vo.AuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SeckillUserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping(value = "/login", produces = "application/json")
    public Result<String> login(HttpServletResponse response, @Valid AuthVo authVo) {
        log.info(authVo.toString());
        String token = userService.login(response, authVo);
        return Result.success(token);
    }
}
