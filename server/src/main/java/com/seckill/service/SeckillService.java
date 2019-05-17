package com.seckill.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillKey;
import com.seckill.util.MD5Util;
import com.seckill.util.UUIDUtil;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("restriction")
@Service
public class SeckillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOrder(goods.getId());
            return null;
        }
    }

    private void setGoodsOrder(long goodsId) {
        try {
            redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public long getSeckillResult(long userId, long goodsId) {
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {// Operation succeeds
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

    public boolean checkPath(SeckillUser user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String pathOld = null;
        try {
            pathOld = redisService.get(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.equals(pathOld);
    }

    public String createSeckillPath(SeckillUser user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        try {
            redisService.set(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }


}
