package com.quickpay.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.quickpay.commons.constant.ConstantKeys;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown  = true)
@Data
public class ResponeseDto<T> {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int code;
    private String msg;
    private boolean success = false;
    private T data = null;

    public static ResponeseDto<Object> success(){
        return success(null);
    }
    public static <T> ResponeseDto<T> success(T data){
        ResponeseDto<T> result = new ResponeseDto<>();
        result.setSuccess(true);
        result.setCode(ConstantKeys.OK_200);
        result.setData(data);
        result.setMsg(ConstantKeys.OPERATION_SUCCESS);
        return  result;
    }
    public static ResponeseDto<Object> fail(String errMsg){
        return fail(500, errMsg);
    }
    public static <T> ResponeseDto<T> fail(Integer errCode ,String errMsg){
        ResponeseDto<T> result = new ResponeseDto<>();
        result.setSuccess(false);
        result.setCode(errCode);
        result.setMsg(errMsg);
        return  result;
    }
}
