package com.hkbu.comp7640.response;

public enum ResponseEnum {

    SUCCESS(200, "SUCCESS"),

    FAILED(400, "FAILED"),

    EXCEPTION(500, "服务器发生异常"),

    METHOD_ARGUMENT_NOT_VALID(40000, "方法参数验证失败"),

    INVALID_PASSWORD(40001, "密码不正确"),

    UNKNOWN_USER(40004, "用户不存在"),

    UNKNOWN_VENDOR(40005, "商户不存在");

    private final Integer code;

    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer value() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
