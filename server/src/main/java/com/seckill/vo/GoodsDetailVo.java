package com.seckill.vo;

import com.seckill.domain.SeckillUser;
import com.seckill.enums.SeckillStatus;

public class GoodsDetailVo {
    private SeckillStatus seckillStatus = SeckillStatus.WAITING;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private SeckillUser user;

    public SeckillStatus getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(SeckillStatus seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public SeckillUser getUser() {
        return user;
    }

    public void setUser(SeckillUser user) {
        this.user = user;
    }
}
