package com.seckill.controller;

import com.seckill.redis.RedisService;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.service.SeckillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SeckillController {

    @Autowired
    private SeckillUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

}
