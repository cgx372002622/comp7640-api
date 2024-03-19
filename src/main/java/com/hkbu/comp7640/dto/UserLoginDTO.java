package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录参数")
public class UserLoginDTO {

    @NotBlank(message = "username不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "password不能为空")
    @Schema(description = "密码")
    private String password;

}
