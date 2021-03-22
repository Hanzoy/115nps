package com.hanzoy.nps.exception;

import com.hanzoy.nps.pojo.dto.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomErrorException.class)
    public CommonResult customExceptionHandler(CustomErrorException e){
        return CommonResult.fail(e.getCode(), e.getMessage());
    }
}
