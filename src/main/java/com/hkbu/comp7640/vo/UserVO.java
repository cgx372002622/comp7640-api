package com.hkbu.comp7640.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "联系方式")
    private String contactNumber;

    @Schema(description = "送货地址")
    private String shippingDetails;

}
