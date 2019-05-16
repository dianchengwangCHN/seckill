package com.seckill.controller;

import com.seckill.domain.SeckillUser;
import com.seckill.dto.Result;
import com.seckill.enums.SeckillStatus;
import com.seckill.redis.RedisService;
import com.seckill.service.GoodsService;
import com.seckill.service.SeckillUserService;
import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private SeckillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/detail/{goodsId}")
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, SeckillUser user,
                                        @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        SeckillStatus seckillStatus = SeckillStatus.WAITING;
        int remainSeconds = 0;
        if (now < startAt) {
            seckillStatus = SeckillStatus.WAITING;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            seckillStatus = SeckillStatus.FINISHED;
            remainSeconds = -1;
        } else {
            seckillStatus = SeckillStatus.PROCEEDING;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSeckillStatus(seckillStatus);
        return Result.success(vo);
    }
}
