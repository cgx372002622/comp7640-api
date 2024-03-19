package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "商铺信息DTO")
public class VendorDTO {

    @Schema(description = "商家id")
    private Long vendorId;

    @Schema(description = "商铺名")
    private String businessName;

    @Schema(description = "商铺评分")
    private Float score;

    @Schema(description = "商家地址")
    private String geographicalPresence;

    @Schema(description = "商铺的商品")
    private List<ProductDTO> productList;

}
