package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "商品DTO")
public class ProductDTO {

    @Schema(description = "商品id", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer productId;

    @Schema(description = "商品名")
    @NotBlank
    private String productName;

    @Schema(description = "商品标价")
    @NotNull
    private BigDecimal listedPrice;

    @Schema(description = "商品标签, 用','分隔, ','前后不能有空格")
    private String tags;

    @Schema(description = "商品库存")
    @NotNull
    private Integer inventory;

    @Schema(description = "商家id")
    @NotNull
    private Integer vendorId;

    @Schema(description = "商品图片地址url")
    private String imgUrl;

}
