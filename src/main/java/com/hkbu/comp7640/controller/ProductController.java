package com.hkbu.comp7640.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hkbu.comp7640.dto.ProductDTO;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.ProductService;
import com.hkbu.comp7640.utils.PageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Tag(name = "商品接口")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/getPageProducts")
    @Operation(summary = "分页商品列表" , description = "根据商品名获取商品分页列表，支持模糊查询及标签查询")
    @Parameters({
            @Parameter(name = "productName", description = "商品关键字，支持模糊查询"),
            @Parameter(name = "tags", description = "商品tag, 多个tag请用','分隔, 注意，','前后不要有空格"),
    })
    public ServerResponseEntity<IPage<ProductDTO>> getPageProducts(
            @Valid @RequestParam(value = "productName", required = false) String productName,
            @Valid @RequestParam(value = "tags", required = false) String tags,
            @Valid @RequestBody(required = false) PageParam<ProductDTO> page) {
        List<String> tagList = null;
        if (StrUtil.isNotBlank(tags)) {
            tagList = StrUtil.split(tags, ",");
        }
        IPage<ProductDTO> productPage = productService.pageByProductName(page, productName, tagList);
        return ServerResponseEntity.success(productPage);
    }

}

