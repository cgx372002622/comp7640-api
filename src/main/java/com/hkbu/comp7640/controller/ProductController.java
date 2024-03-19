package com.hkbu.comp7640.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hkbu.comp7640.dto.ProductWithVendorDTO;
import com.hkbu.comp7640.entity.Product;
import com.hkbu.comp7640.entity.Vendor;
import com.hkbu.comp7640.exception.MyBindException;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.ProductService;
import com.hkbu.comp7640.service.VendorService;
import com.hkbu.comp7640.utils.PageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private VendorService vendorService;

    @PostMapping("/getPageProducts")
    @Operation(summary = "分页商品列表" , description = "根据商品名获取商品分页列表，支持模糊查询及标签查询")
    @Parameters({
            @Parameter(name = "productName", description = "商品关键字，支持模糊查询"),
            @Parameter(name = "tags", description = "商品tag, 多个tag请用','分隔, 注意，','前后不要有空格"),
    })
    public ServerResponseEntity<IPage<ProductWithVendorDTO>> getPageProducts(
            @Valid @RequestParam(value = "productName", required = false) String productName,
            @Valid @RequestParam(value = "tags", required = false) String tags,
            @Valid @RequestBody(required = false) PageParam<ProductWithVendorDTO> page) {
        List<String> tagList = null;
        if (StrUtil.isNotBlank(tags)) {
            tagList = StrUtil.split(tags, ",");
        }
        IPage<ProductWithVendorDTO> productPage = productService.pageByProductName(page, productName, tagList);
        return ServerResponseEntity.success(productPage);
    }

    @GetMapping("/getProductById/{productId}")
    @Operation(summary = "查询一件商品" , description = "根据商品id查询商品信息")
    @Parameters({
            @Parameter(name = "productId", description = "商品id"),
    })
    public ServerResponseEntity<ProductWithVendorDTO> getProductById(@Valid @PathVariable("productId") String productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new MyBindException("该商品不存在");
        }
        Vendor vendor = vendorService.getOne(new LambdaQueryWrapper<Vendor>().eq(Vendor::getVendorId, product.getVendorId()));
        ProductWithVendorDTO productWithVendorDTO = new ProductWithVendorDTO();
        BeanUtils.copyProperties(product, productWithVendorDTO);
        BeanUtils.copyProperties(vendor, productWithVendorDTO);
        return ServerResponseEntity.success(productWithVendorDTO);
    }

}

