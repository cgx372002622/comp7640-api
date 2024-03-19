package com.hkbu.comp7640.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "transaction_id", type = IdType.AUTO)
    private String transactionId;

    private String userId;

    private String productId;

    private Integer amount;

    private String status;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Product product;

}
