package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "商品DTO")
public class ProductDTO {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "商品名")
    private String productName;

    @Schema(description = "商品标价")
    private BigDecimal listedPrice;

    @Schema(description = "商品标签")
    private String tags;

    @Schema(description = "商品库存")
    private Integer inventory;

    @Schema(description = "商家id")
    private Long vendorId;

    @Schema(description = "商品图片地址url")
    private String imgUrl;

}
