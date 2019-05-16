package com.seckill.redis;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
