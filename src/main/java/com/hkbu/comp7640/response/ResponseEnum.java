package com.hkbu.comp7640.response;

public enum ResponseEnum {

    SUCCESS(200, "SUCCESS"),

    FAILED(400, "FAILED"),

    EXCEPTION(500, "服务器发生异常"),

    METHOD_ARGUMENT_NOT_VALID(40000, "方法参数验证失败"),

    INVALID_PASSWORD(40001, "密码不正确"),

    UNKNOWN_USER(40004, "用户不存在"),

    UNKNOWN_VENDOR(40005, "商户不存在"),

    TRANSACTION_NOT_FOUND(40010, "该订单不存在"),

    CANNOT_UPDATE_TRANSACTION(40011, "该订单已发货，无法修改"),

    CANNOT_DELETE_TRANSACTION(40012, "该订单已发货，无法删除"),

    DELETE_TRANSACTION_FAILED(40013, "订单删除失败"),

    UPDATE_TRANSACTION_FAILED(40014, "订单修改失败"),

    INSERT_TRANSACTION_FAILED(40015, "新增订单失败"),

    INSERT_TRANSACTION_FAILED_NO_INVENTORY(40016, "新增订单失败，库存不足"),

    UPDATE_TRANSACTION_FAILED_NO_INVENTORY(40017, "更新订单失败，库存不足");

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
