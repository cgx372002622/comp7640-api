package com.hkbu.comp7640.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String productId;

    private String productName;

    private BigDecimal listedPrice;

    private String tags;

    private Integer inventory;

    private String vendorId;

    private String businessName;

    private Float score;

    private String geographicalPresence;

}
