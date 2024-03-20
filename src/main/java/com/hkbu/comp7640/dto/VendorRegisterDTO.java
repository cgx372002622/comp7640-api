package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "商家注册DTO")
public class VendorRegisterDTO {

    @Schema(description = "商户名")
    @NotBlank
    private String vendorName;

    @Schema(description = "密码")
    @NotBlank
    private String password;

    @Schema(description = "商铺昵称")
    @NotBlank
    private String businessName;

    @Schema(description = "商铺地理位置")
    private String geographicalPresence;

}
