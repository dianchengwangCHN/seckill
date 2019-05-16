package com.seckill.dao;

import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderDao {

    @Select("select * from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SeckillOrder getSeckillOrderByUserIdGoodsId(@Param("userId")long userId, @Param("goodsId")long goodsId);

    @Insert("insert into order_info (user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status," +
            "create _date)values(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, " +
            "#{status}, #{createDate})")
    long insert(OrderInfo orderInfo);

    @Insert("insert into seckill_order (user_id, goods_id, order_id)values(#{userId}")
    int insertSeckillOrder(SeckillOrder seckillOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from seckill_order")
    void deleteSeckillOrders();
}
