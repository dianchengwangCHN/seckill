package com.seckill.exception;

import com.seckill.enums.CodeMsg;

public class SeckillException extends RuntimeException {

    private CodeMsg codeMsg;

    public SeckillException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
