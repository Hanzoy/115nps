package com.hanzoy.nps.exception;

import lombok.Data;

@Data
public class CustomErrorException extends RuntimeException{
    private String code;
    public CustomErrorException(String code, String message) {
        super(message);
        this.code = code;
    }
}
