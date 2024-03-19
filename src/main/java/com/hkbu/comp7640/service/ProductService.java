package com.hkbu.comp7640.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.ProductWithVendorDTO;
import com.hkbu.comp7640.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据商品名分页获取商品列表
     * @param page 分页参数
     * @param productName 商品关键字
     * @return
     */
    IPage<ProductWithVendorDTO> pageByProductName(Page<ProductWithVendorDTO> page, String productName, List<String> tagList);

}
