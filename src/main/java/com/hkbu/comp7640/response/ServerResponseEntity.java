package com.hkbu.comp7640.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class ServerResponseEntity<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public static <T> ServerResponseEntity<T> success() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.value());
        serverResponseEntity.setMessage(ResponseEnum.SUCCESS.getMessage());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(T data, String message) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.value());
        serverResponseEntity.setMessage(message);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.FAILED.value());
        serverResponseEntity.setMessage(ResponseEnum.FAILED.getMessage());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.FAILED.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(Integer code, String message) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(code);
        serverResponseEntity.setMessage(message);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(T data, String message) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.FAILED.value());
        serverResponseEntity.setMessage(message);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(responseEnum.value());
        serverResponseEntity.setMessage(responseEnum.getMessage());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(responseEnum.value());
        serverResponseEntity.setMessage(responseEnum.getMessage());
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

}
