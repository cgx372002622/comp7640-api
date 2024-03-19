package com.hkbu.comp7640.exception;

import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import lombok.Getter;

@Getter
public class UnAuthorizationException extends RuntimeException {

    private Integer code;

    private Object object;

    private ServerResponseEntity<?> serverResponseEntity;

    public UnAuthorizationException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.value();
    }

    public UnAuthorizationException(ResponseEnum responseEnum, String message) {
        super(message);
        this.code = responseEnum.value();
    }

    public UnAuthorizationException(ServerResponseEntity<?> serverResponseEntity) {
        this.serverResponseEntity = serverResponseEntity;
    }

    public UnAuthorizationException(String message) {
        super(message);
        this.code = ResponseEnum.FAILED.value();
    }

    public UnAuthorizationException(String message, Object object) {
        super(message);
        this.code = ResponseEnum.FAILED.value();
        this.object = object;
    }

}
