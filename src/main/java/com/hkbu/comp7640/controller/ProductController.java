package com.hkbu.comp7640.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.ProductDTO;
import com.hkbu.comp7640.dto.ProductUpdateDTO;
import com.hkbu.comp7640.dto.ProductWithVendorDTO;
import com.hkbu.comp7640.entity.Product;
import com.hkbu.comp7640.entity.Vendor;
import com.hkbu.comp7640.exception.MyBindException;
import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.ProductService;
import com.hkbu.comp7640.service.VendorService;
import com.hkbu.comp7640.utils.PageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            @Parameter(name = "productName", description = "商品关键字，支持模糊查询", in = ParameterIn.QUERY),
            @Parameter(name = "tags", description = "商品tag, 多个tag请用','分隔, 注意，','前后不要有空格", in = ParameterIn.QUERY),
    })
    public ServerResponseEntity<IPage<ProductWithVendorDTO>> getPageProducts(
            @Valid @RequestParam(value = "productName", required = false) String productName,
            @Valid @RequestParam(value = "tags", required = false) String tags,
            @RequestBody(required = false) PageParam<ProductWithVendorDTO> page) {
        if (page == null) {
            page = new PageParam<>();
        }
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
            @Parameter(name = "productId", description = "商品id", in = ParameterIn.PATH),
    })
    public ServerResponseEntity<ProductWithVendorDTO> getProductById(@Valid @PathVariable("productId") Integer productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new MyBindException(ResponseEnum.UNKNOWN_PRODUCT);
        }
        Vendor vendor = vendorService.getOne(new LambdaQueryWrapper<Vendor>().eq(Vendor::getVendorId, product.getVendorId()));
        ProductWithVendorDTO productWithVendorDTO = new ProductWithVendorDTO();
        BeanUtils.copyProperties(product, productWithVendorDTO);
        BeanUtils.copyProperties(vendor, productWithVendorDTO);
        return ServerResponseEntity.success(productWithVendorDTO);
    }

    @PostMapping("/getPageProductsByVendorId")
    @Operation(summary = "分页商铺的商品列表" , description = "根据商铺id获取商品分页列表")
    @Parameters({
            @Parameter(name = "vendorId", description = "商铺id", in = ParameterIn.QUERY),
    })
    public ServerResponseEntity<IPage<ProductDTO>> getPageProductsByVendorId(
            @Valid @RequestParam(value = "vendorId") Integer vendorId,
            @RequestBody(required = false) PageParam<Product> page
    ) {
        if (page == null) {
            page = new PageParam<>();
        }
        Vendor vendor = vendorService.getById(vendorId);
        if (vendor == null) {
            throw new MyBindException(ResponseEnum.UNKNOWN_VENDOR);
        }
        IPage<ProductDTO> productPage = productService.page(page, new LambdaQueryWrapper<Product>()
                        .eq(Product::getVendorId, vendorId)).convert(product -> {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            return productDTO;
        });
        return ServerResponseEntity.success(productPage);
    }

    @PostMapping("/insertProduct")
    @Operation(summary = "新增一件商品" , description = "给商铺新增一件商品")
    public ServerResponseEntity<?> insertProduct(
            @Valid @RequestBody ProductDTO productDTO
    ) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        product.setTags(Optional.ofNullable(product.getTags()).orElse(""));
//        boolean isValid = isValidTagString(product.getTags().trim());
//        if (!isValid) {
//            throw new MyBindException(ResponseEnum.TAG_IS_INVALID);
//        }
        boolean success = productService.save(product);
        return success ?
                ServerResponseEntity.success("新增商品成功") :
                ServerResponseEntity.success(ResponseEnum.INSERT_PRODUCT_FAILED);
    }

    @PutMapping("/updateProductById/{productId}")
    @Operation(summary = "修改一件商品" , description = "给商铺修改一件商品")
    @Parameters({
            @Parameter(name = "productId", description = "商品id", in = ParameterIn.PATH),
    })
    public ServerResponseEntity<?> updateProduct(
            @PathVariable(name = "productId") Integer productId,
            @RequestBody ProductDTO productDTO
    ) {
        Product product = productService.getById(productId);
        if (product == null ) {
            throw new MyBindException(ResponseEnum.UNKNOWN_PRODUCT);
        }
        BeanUtils.copyProperties(productDTO, product);
        product.setProductId(productId);
        boolean success = productService.updateById(product);
        return success ?
                ServerResponseEntity.success("修改商品成功") :
                ServerResponseEntity.success(ResponseEnum.UPDATE_PRODUCT_FAILED);
    }

    @PutMapping("/updateBatchProduct")
    @Operation(summary = "批量修改商品" , description = "给商铺批量修改商品")
    public ServerResponseEntity<?> updateBatchProduct(
            @RequestBody List<ProductUpdateDTO> productDTOList
    ) {
        ArrayList<Product> products = new ArrayList<>();
        for (ProductUpdateDTO productUpdateDTO : productDTOList) {
            Product product = new Product();
            BeanUtils.copyProperties(productUpdateDTO, product);
            products.add(product);
        }
        boolean success = productService.updateBatchById(products);
        return success ?
                ServerResponseEntity.success("批量修改商品成功") :
                ServerResponseEntity.success(ResponseEnum.UPDATE_BATCH_PRODUCT_FAILED);
    }

    @DeleteMapping("/deleteProductById/{productId}")
    @Operation(summary = "删除一件商品" , description = "给商铺删除一件商品")
    @Parameters({
            @Parameter(name = "productId", description = "商品id", in = ParameterIn.PATH),
    })
    public ServerResponseEntity<?> deleteProductById(
            @PathVariable(name = "productId") Integer productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            throw new MyBindException(ResponseEnum.UNKNOWN_PRODUCT);
        }
        boolean success = productService.removeById(productId);
        return success ?
                ServerResponseEntity.success("删除商品成功") :
                ServerResponseEntity.success(ResponseEnum.DELETE_PRODUCT_FAILED);
    }

//    /**
//     * TODO 判断tag是否满足需求-用','分隔且','前后没有空格
//     * @param inputString
//     * @return
//     */
//    private boolean isValidTagString(String inputString) {
//        // 使用正则表达式匹配逗号前后是否有空格
//        String regex = "^(?!.*,$)(\\\\S+,)*(\\\\S+)$";
//
//        // 检查输入字符串是否符合正则表达式规则
//        return inputString.matches(regex);
//    }

}

