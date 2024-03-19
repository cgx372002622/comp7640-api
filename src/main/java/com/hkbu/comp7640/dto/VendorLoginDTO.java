package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "商户登录参数")
public class VendorLoginDTO {

    @NotBlank(message = "vendorName不能为空")
    @Schema(description = "商户名")
    private String vendorName;

    @NotBlank(message = "password不能为空")
    @Schema(description = "密码")
    private String password;

}
