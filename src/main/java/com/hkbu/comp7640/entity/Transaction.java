package com.hkbu.comp7640.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "交易")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "transaction_id", type = IdType.AUTO)
    @Schema(description = "交易id", accessMode = Schema.AccessMode.READ_ONLY)
    private Long transactionId;

    @NotNull
    @Schema(description = "用户id")
    private Long userId;

    @NotNull
    @Schema(description = "商品id")
    private Long productId;

    @NotNull
    @Schema(description = "购买数量")
    private Integer amount;

    @Schema(description = "状态", accessMode = Schema.AccessMode.READ_ONLY)
    private String status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    @TableField(exist = false)
    @Hidden
    private User user;

    @TableField(exist = false)
    @Hidden
    private Product product;

}
