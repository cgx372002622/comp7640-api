package com.hkbu.comp7640.exception;

import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import lombok.Getter;

@Getter
public class MyBindException extends RuntimeException {

    private Integer code;

    private Object object;

    private ServerResponseEntity<?> serverResponseEntity;

    public MyBindException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.value();
    }

    public MyBindException(ResponseEnum responseEnum, String message) {
        super(message);
        this.code = responseEnum.value();
    }

    public MyBindException(ServerResponseEntity<?> serverResponseEntity) {
        this.serverResponseEntity = serverResponseEntity;
    }

    public MyBindException(String message) {
        super(message);
        this.code = ResponseEnum.FAILED.value();
    }

    public MyBindException(String message, Object object) {
        super(message);
        this.code = ResponseEnum.FAILED.value();
        this.object = object;
    }

}
