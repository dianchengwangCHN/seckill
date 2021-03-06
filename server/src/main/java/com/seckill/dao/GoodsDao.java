package com.seckill.dao;

import com.seckill.domain.SeckillGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.seckill_price from seckill_goods mg left join " +
            "goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.seckill_price from seckill_goods mg left join " +
            "goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SeckillGoods g);

    @Update("update seckill_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(SeckillGoods g);
}
