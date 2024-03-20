package com.hkbu.comp7640.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hkbu.comp7640.dto.ProductDTO;
import com.hkbu.comp7640.dto.VendorDTO;
import com.hkbu.comp7640.dto.VendorRegisterDTO;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Tag(name = "商铺接口")
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductService productService;

    @PostMapping("/getPageVendors")
    @Operation(summary = "分页商铺列表", description = "可根据商铺名筛选分页商铺列表")
    @Parameters({
            @Parameter(name = "businessName", description = "商铺名，支持模糊查询", in = ParameterIn.QUERY),
    })
    public ServerResponseEntity<IPage<VendorDTO>> getPageVendors(
            @RequestParam(value = "businessName", required = false) String businessName,
            @RequestBody(required = false) PageParam<Vendor> page) {
        if (page == null) {
            page = new PageParam<>();
        }
        IPage<VendorDTO> vendorPage = vendorService.page(page,
                new LambdaQueryWrapper<Vendor>().likeLeft(StrUtil.isNotBlank(businessName), Vendor::getBusinessName, businessName))
                .convert(vendor -> {
                    List<ProductDTO> productList = productService.list(new LambdaQueryWrapper<Product>().eq(Product::getVendorId, vendor.getVendorId()))
                            .stream().map(product -> {
                                ProductDTO productDTO = new ProductDTO();
                                BeanUtils.copyProperties(product, productDTO);
                                return productDTO;
                            }).toList();
                    VendorDTO vendorDTO = new VendorDTO();
                    BeanUtils.copyProperties(vendor,vendorDTO);
                    vendorDTO.setProductList(productList);
                    return vendorDTO;
                });
        return ServerResponseEntity.success(vendorPage);
    }

    @GetMapping("/getVendorById/{vendorId}")
    @Operation(summary = "查询一条商家信息", description = "根据商家id查询商家信息")
    @Parameters({
            @Parameter(name = "vendorId", description = "商家id", in = ParameterIn.PATH),
    })
    public ServerResponseEntity<VendorDTO> getVendorById(@PathVariable("vendorId") Long vendorId) {
        Vendor vendor = vendorService.getById(vendorId);

        if (vendor == null) {
            throw new MyBindException(ResponseEnum.UNKNOWN_VENDOR);
        }

        List<ProductDTO> products = productService.list(new LambdaQueryWrapper<Product>().eq(Product::getVendorId, vendorId)).stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    BeanUtils.copyProperties(product, productDTO);
                    return productDTO;
                }).toList();

        VendorDTO vendorDTO = new VendorDTO();
        BeanUtils.copyProperties(vendor, vendorDTO);
        vendorDTO.setProductList(products);
        return ServerResponseEntity.success(vendorDTO);
    }

    @PostMapping("/registerVendor")
    @Operation(summary = "商家注册", description = "商家注册")
    public ServerResponseEntity<?> registerVendor(@Valid @RequestBody VendorRegisterDTO vendorRegisterDTO) {
        try {
            Vendor vendor = new Vendor();
            BeanUtils.copyProperties(vendorRegisterDTO, vendor);
            DecimalFormat df = new DecimalFormat(".0");
            vendor.setScore(Float.parseFloat(df.format(new Random().nextFloat() * 10)));
            boolean success = vendorService.save(vendor);
            return success ?
                    ServerResponseEntity.success("注册成功") :
                    ServerResponseEntity.success(ResponseEnum.REGISTER_VENDOR_FAILED);
        } catch (DuplicateKeyException e) {
            return ServerResponseEntity.success(ResponseEnum.DUPLICATED_VENDOR_NAME);
        }
    }

}

