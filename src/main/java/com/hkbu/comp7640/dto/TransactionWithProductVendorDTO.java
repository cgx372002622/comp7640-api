package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "交易记录附带用户及商品信息DTO")
public class TransactionWithProductVendorDTO {

    @Schema(description = "交易id")
    private Integer transactionId;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "交易数量")
    private Integer amount;

    @Schema(description = "订单状态, '0'-待发货，可修改删除；'1'-已发货，不可修改及删除")
    private String status;

    @Schema(description = "交易时间，'pattern=\"yyyy-MM-dd HH:mm:ss\"'")
    private Date dateTime;

    @Schema(description = "商品名")
    private String productName;

    @Schema(description = "商品标价")
    private BigDecimal listedPrice;

    @Schema(description = "商品标签")
    private String tags;

    @Schema(description = "商品库存")
    private Integer inventory;

    @Schema(description = "商家id")
    private Integer vendorId;

    @Schema(description = "商品图片地址url")
    private String imgUrl;

    @Schema(description = "商铺名")
    private String businessName;

    @Schema(description = "商铺评分")
    private Float score;

    @Schema(description = "商家地址")
    private String geographicalPresence;


}
