package com.quickpay.commons.exceptions;

public class AuthorizeException extends RuntimeException{
    private int code;
    private String msg;

    public AuthorizeException(String msg){
        super(msg);
    }
    public AuthorizeException(int code ,String msg){
        super(msg);
        this.code = code;
    }
    public AuthorizeException(int code , String msg, Throwable cause){
        super(msg, cause);
        this.code = code;
    }
}
