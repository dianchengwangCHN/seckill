package com.seckill.controller;

import com.seckill.redis.RedisService;
import com.seckill.service.SeckillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private SeckillUserService userService;

    @Autowired
    private RedisService redisService;

}
