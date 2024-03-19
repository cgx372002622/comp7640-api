package com.hkbu.comp7640.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private String productId;

    private String productName;

    private BigDecimal listedPrice;

    private String tags;

    private Integer inventory;

    private String vendorId;

    @TableField(exist = false)
    private Vendor vendor;

    private String imgUrl;

}
