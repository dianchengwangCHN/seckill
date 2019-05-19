package com.seckill.enums;

public enum CodeMsg {
    SUCCESS(0, "Success"),
    SERVER_ERROR(500100, "Server error"),
    REQUEST_ILLEGAL(500102, "Illegal request"),
    ACCESS_LIMIT_EXCEEDED(500104, "Access limit exceeded"),
    SESSION_ERROR(500210, "Session does not exists or has expired"),
    SECKILL_OVER(500500, "Seckill has closed"),
    REPEAT_KILL(500501, "Cannot repeat purchase"),
    SECKILL_FAIL(500502, "Operation fails"),
    ;

    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
