package com.hkbu.comp7640.response;

public enum ResponseEnum {

    SUCCESS(200, "SUCCESS"),

    FAILED(400, "FAILED"),

    EXCEPTION(500, "服务器发生异常"),

    METHOD_ARGUMENT_NOT_VALID(40000, "方法参数验证失败"),

    INVALID_PASSWORD(40001, "密码不正确"),

    UNKNOWN_USER(40004, "用户不存在"),

    UNKNOWN_VENDOR(40005, "商户不存在"),

    UNKNOWN_PRODUCT(40006, "商品不存在"),

    TRANSACTION_NOT_FOUND(40010, "该订单不存在"),

    CANNOT_UPDATE_TRANSACTION(40011, "该订单已发货，无法修改"),

    CANNOT_DELETE_TRANSACTION(40012, "该订单已发货，无法删除"),

    DELETE_TRANSACTION_FAILED(40013, "订单删除失败"),

    UPDATE_TRANSACTION_FAILED(40014, "订单修改失败"),

    INSERT_TRANSACTION_FAILED(40015, "新增订单失败"),

    INSERT_TRANSACTION_FAILED_NO_INVENTORY(40016, "新增订单失败，库存不足"),

    UPDATE_TRANSACTION_FAILED_NO_INVENTORY(40017, "更新订单失败，库存不足"),

    INSERT_PRODUCT_FAILED(40018, "新增商品失败"),

    TAG_IS_INVALID(40019, "传入参数tags不满足要求"),

    UPDATE_PRODUCT_FAILED(40020, "修改商品失败"),

    DELETE_PRODUCT_FAILED(40021, "删除商品失败"),

    REGISTER_VENDOR_FAILED(40022, "注册商家失败"),

    DUPLICATED_VENDOR_NAME(40023, "商户名已存在"),

    REGISTER_USER_FAILED(40024, "注册商家失败"),

    DUPLICATED_USER_NAME(40025, "用户名已存在"),

    UPDATE_BATCH_PRODUCT_FAILED(40026, "批量修改商品失败"),

    INSERT_BATCH_TRANSACTION_FAILED(40027, "批量新增订单失败"),

    DELETE_BATCH_TRANSACTION_FAILED(40028, "批量删除订单失败，查看data字段，获取删除失败的订单id");

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
