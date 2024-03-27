package com.quickpay.commons.exceptions;

import com.quickpay.business.dto.ResponeseDto;
import com.quickpay.commons.context.ContextKey;
import com.quickpay.commons.context.ThreadContext;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponeseDto<?> handleBusinessException(HttpServletRequest request, BusinessException ex){
        log.info(">> business exception: {}, {}, {}", request.getRequestURI(), ex.getCode(), ex.getMessage());
        String errMessage = ex.getMessage();
        //default business exception message
        if (StringUtils.isBlank(errMessage)) {
            errMessage = "Server is busy";
            log.warn(">> Empty business exception error message", ex);
        }
        return ResponeseDto.fail(HttpStatus.BAD_REQUEST.value(), errMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponeseDto<String> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex){
        log.info(">>illegal argument error: {} {}", request.getRequestURI(), ex.getMessage());

        return ResponeseDto.fail(HttpStatus.BAD_REQUEST.value(), ex.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponeseDto<?> handleMethodArgNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex){
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.info(">> argument not valid error: {} {}", request.getRequestURI(), ex.getMessage());
        String field = fieldError == null ? "unknown" : fieldError.getField();
        String message = fieldError == null ? "null" : fieldError.getDefaultMessage();
        return ResponeseDto.fail(HttpStatus.BAD_REQUEST.value(), "Invalid request parameter " + field + " : " + message);
        }

        @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponeseDto<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.info(">> method not supported error: {} {}, expected: {}",
                ex.getMethod(), request.getRequestURI(), ex.getSupportedHttpMethods());
        return ResponeseDto.fail(HttpStatus.METHOD_NOT_ALLOWED.value(),  ex.getMethod()+" method is not supported" );


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponeseDto<?> handleMissingParameter(HttpServletRequest req, MissingServletRequestParameterException ex){
        log.error(">>{} missing parameter {}",req.getRequestURI(), ex.getParameterName());
        return ResponeseDto.fail(HttpStatus.BAD_REQUEST.value(), "missing parameter "+ex.getParameterName());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizeException.class)
    public ResponeseDto<?> handleAuthorizeException(HttpServletRequest req , AuthorizeException ex){
        log.warn(">> ip: {} unauthorized request to {}", ThreadContext.get(ContextKey.CLIENT_IP), req
                .getRequestURI());

        return ResponeseDto.fail(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponeseDto<?> defaultErrorHandler(HttpServletRequest request , Exception ex){
        log.error(">>Server internal error "+request.getRequestURI(),  ex);
        return ResponeseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server is busy");
    }


}
