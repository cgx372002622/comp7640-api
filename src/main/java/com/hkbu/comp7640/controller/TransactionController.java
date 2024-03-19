package com.hkbu.comp7640.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hkbu.comp7640.dto.TransactionWithProductVendorDTO;
import com.hkbu.comp7640.entity.Product;
import com.hkbu.comp7640.entity.Transaction;
import com.hkbu.comp7640.entity.Vendor;
import com.hkbu.comp7640.exception.MyBindException;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.ProductService;
import com.hkbu.comp7640.service.TransactionService;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Tag(name = "交易订单接口")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private VendorService vendorService;

    @PostMapping("/getPageTransactions")
    @Operation(summary = "分页交易列表" , description = "获取交易分页列表，包括商品信息及对应的商铺信息")
    public ServerResponseEntity<IPage<TransactionWithProductVendorDTO>> getPageTransactions(
            @Valid @RequestBody(required = false) PageParam<TransactionWithProductVendorDTO> page) {
        IPage<TransactionWithProductVendorDTO> transactionsPage = transactionService.pageTransaction(page);
        return ServerResponseEntity.success(transactionsPage);
    }

    @GetMapping("/getTransactionById/{transactionId}")
    @Operation(summary = "获取一条交易订单" , description = "根据id获取交易订单")
    @Parameters({
            @Parameter(name = "transactionId", description = "交易订单id", in = ParameterIn.PATH)
    })
    public ServerResponseEntity<TransactionWithProductVendorDTO> getTransactionById(
            @Valid @PathVariable("transactionId") String transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new MyBindException("该订单交易不存在");
        }
        Product product = productService.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, transaction.getProductId()));
        Vendor vendor = vendorService.getOne(new LambdaQueryWrapper<Vendor>().eq(Vendor::getVendorId, product.getVendorId()));
        TransactionWithProductVendorDTO dto = new TransactionWithProductVendorDTO();
        BeanUtils.copyProperties(vendor, dto);
        BeanUtils.copyProperties(product, dto);
        BeanUtils.copyProperties(transaction, dto);
        return ServerResponseEntity.success(dto);
    }

}

