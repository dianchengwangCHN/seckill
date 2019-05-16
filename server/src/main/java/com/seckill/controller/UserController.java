package com.seckill.controller;

import com.seckill.domain.SeckillUser;
import com.seckill.dto.Result;
import com.seckill.redis.RedisService;
import com.seckill.service.SeckillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SeckillUserService seckillUserService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/info")
    public Result<SeckillUser> info(SeckillUser user) {
        return Result.success(user);
    }
}
