package com.hkbu.comp7640.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.ProductDTO;
import com.hkbu.comp7640.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    IPage<ProductDTO> pageByProductName(Page<ProductDTO> page, @Param("productName") String productName, @Param("tagList") List<String> tagList);

}
