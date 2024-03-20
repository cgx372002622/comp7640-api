package com.hkbu.comp7640.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "交易记录修改前端传参")
public class UpdateTransactionDTO {

    @Schema(description = "商品id")
    @NotNull
    private Integer transactionId;

    @Schema(description = "需修改的商品交易数量")
    @NotNull
    private Integer amount;

}
