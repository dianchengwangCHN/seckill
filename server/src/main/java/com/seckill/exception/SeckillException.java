package com.seckill.exception;

import com.seckill.enums.SeckillStatus;

public class SeckillException extends RuntimeException {

    private SeckillStatus status;

    public SeckillException(SeckillStatus status) {
        super(status.toString());
        this.status = status;
    }

    public SeckillStatus getStatus() {
        return status;
    }

}
