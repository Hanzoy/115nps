package com.hanzoy.nps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult {
    private String code;
    private String message;
    private Object data;

    public CommonResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResult success(String code, String message, Object data){
        return new CommonResult(code, message, data);
    }

    public static CommonResult success(String code, String message){
        return new CommonResult(code, message);
    }

    public static CommonResult success(Object data){
        return new CommonResult("00000", "请求成功", data);
    }
    public static CommonResult fail(String code, String message, Object data){
        return new CommonResult(code, message, data);
    }

    public static CommonResult fail(String code, String message){
        return new CommonResult(code, message);
    }

    public static CommonResult fail(Object data){
        return new CommonResult("A0001", "请求失败", data);
    }
}
