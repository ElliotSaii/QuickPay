package com.quickpay.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.quickpay.commons.context.ConstantCode;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown  = true)
@Data
public class ResultVo<T> {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int code;
    private String msg;
    private boolean success = false;
    private T data = null;

    public static ResultVo<Object> success(){
        return success(null);
    }
    public static <T> ResultVo<T> success(T data){
        ResultVo<T> result = new ResultVo<>();
        result.setSuccess(true);
        result.setCode(ConstantCode.OK_200);
        result.setData(data);
        result.setMsg(ConstantCode.OPERATION_SUCCESS);
        return  result;
    }
    public static ResultVo<Object> fail(String errMsg){
        return fail(500, errMsg);
    }
    public static <T> ResultVo<T> fail(Integer errCode ,String errMsg){
        ResultVo<T> result = new ResultVo<>();
        result.setSuccess(false);
        result.setCode(errCode);
        result.setMsg(errMsg);
        return  result;
    }
}
