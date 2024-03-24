package com.quickpay.commons.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private int code;



    public BusinessException(String msg){
        super(msg);
    }
    public BusinessException(int code , String msg){
        super(msg);
        this.code = code;

    }
    public BusinessException(int code , String msg, Throwable cause){
        super(msg , cause);
        this.code = code;
    }
}
