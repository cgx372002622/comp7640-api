package com.hkbu.comp7640.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VendorVO {

    @Schema(description = "商户id")
    private Integer vendorId;

    @Schema(description = "商户名-登录用")
    private String vendorName;

    @Schema(description = "商户昵称-展示用")
    private String businessName;

    @Schema(description = "用户评分")
    private Integer score;

    @Schema(description = "地理位置")
    private String geographicalPresence;

}
