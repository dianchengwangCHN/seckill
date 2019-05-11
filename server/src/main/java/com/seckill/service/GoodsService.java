package com.seckill.service;

import com.seckill.dao.GoodsDao;
import com.seckill.domain.SeckillGoods;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsIf(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(seckillGoods);
        return ret > 0;
    }

    public void resetStock(List<GoodsVo> goodsList) {
        for (GoodsVo goods : goodsList) {
            SeckillGoods seckillGoods = new SeckillGoods();
            seckillGoods.setGoodsId(goods.getId());
            seckillGoods.setStockCount(goods.getStockCount());
            goodsDao.resetStock(seckillGoods);
        }
    }
}
