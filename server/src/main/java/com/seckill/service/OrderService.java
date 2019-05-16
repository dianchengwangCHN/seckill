package com.seckill.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seckill.dao.OrderDao;
import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.redis.OrderKey;
import com.seckill.redis.RedisService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    public SeckillOrder getSeckillOrderByuserIdGoodsId(long userId, long goodsId) {
        try {
            return redisService.get(OrderKey.getSeckillOrderByUidGid, "" + userId + "_" + goodsId, SeckillOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public OrderInfo createOrder(SeckillUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);

        try {
            redisService.set(OrderKey.getSeckillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), seckillOrder);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteSeckillOrders();
    }
}
