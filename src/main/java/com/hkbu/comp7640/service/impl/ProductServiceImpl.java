package com.hkbu.comp7640.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.ProductDTO;
import com.hkbu.comp7640.entity.Product;
import com.hkbu.comp7640.dao.ProductMapper;
import com.hkbu.comp7640.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<ProductDTO> pageByProductName(Page<ProductDTO> page, String productName, List<String> tagList) {
        return productMapper.pageByProductName(page, productName, tagList);
    }
}
