package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "用户注册DTO")
public class UserRegisterDTO {

    @NotBlank
    @Schema(description = "用户名")
    private String userName;

    @NotBlank
    @Schema(description = "密码")
    private String password;

    @NotBlank
    @Schema(description = "昵称")
    private String nickName;

    @NotNull
    @Schema(description = "联系方式")
    private Integer contactNumber;

    @NotBlank
    @Schema(description = "送货地址")
    private String shippingDetails;

}
