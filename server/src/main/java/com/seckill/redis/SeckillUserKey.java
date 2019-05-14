package com.seckill.redis;

public class SeckillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillUserKey getToken = new SeckillUserKey(TOKEN_EXPIRE, "tk");

    public static SeckillUserKey getById = new SeckillUserKey(0, "id");
}
