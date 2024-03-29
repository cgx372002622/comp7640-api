package com.hkbu.comp7640.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vendor_id", type = IdType.AUTO)
    private Integer vendorId;

    private String businessName;

    private String vendorName;

    private Float score;

    private String geographicalPresence;

    private String password;

    @TableField(exist = false)
    private List<Product> productList;

}
