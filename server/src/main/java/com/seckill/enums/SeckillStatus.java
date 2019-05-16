package com.seckill.enums;

public enum SeckillStatus {
    WAITING(0),
    PROCEEDING(1),
    FINISHED(2)
    ;

    private int statusCode;

    SeckillStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
