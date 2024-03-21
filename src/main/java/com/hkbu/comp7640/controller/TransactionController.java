package com.hkbu.comp7640.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hkbu.comp7640.dto.TransactionWithProductVendorDTO;
import com.hkbu.comp7640.dto.UpdateTransactionDTO;
import com.hkbu.comp7640.entity.Product;
import com.hkbu.comp7640.entity.Transaction;
import com.hkbu.comp7640.entity.Vendor;
import com.hkbu.comp7640.exception.MyBindException;
import com.hkbu.comp7640.response.ResponseEnum;
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

import java.util.Date;
import java.util.List;
import java.util.Random;

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
    @Parameters({
            @Parameter(name = "userId", description = "用户id", in = ParameterIn.QUERY)
    })
    public ServerResponseEntity<IPage<TransactionWithProductVendorDTO>> getPageTransactions(
            @Valid @RequestParam(value = "userId", required = true) Integer userId,
            @RequestBody(required = false) PageParam<TransactionWithProductVendorDTO> page) {
        if (page == null) {
            page = new PageParam<>();
        }
        IPage<TransactionWithProductVendorDTO> transactionsPage = transactionService.pageTransaction(page, userId);
        return ServerResponseEntity.success(transactionsPage);
    }

    @GetMapping("/getTransactionById/{transactionId}")
    @Operation(summary = "获取一条交易订单" , description = "根据id获取交易订单")
    @Parameters({
            @Parameter(name = "transactionId", description = "交易订单id", in = ParameterIn.PATH)
    })
    public ServerResponseEntity<TransactionWithProductVendorDTO> getTransactionById(
            @Valid @PathVariable("transactionId") Integer transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new MyBindException(ResponseEnum.TRANSACTION_NOT_FOUND);
        }
        Product product = productService.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, transaction.getProductId()));
        Vendor vendor = vendorService.getOne(new LambdaQueryWrapper<Vendor>().eq(Vendor::getVendorId, product.getVendorId()));
        TransactionWithProductVendorDTO dto = new TransactionWithProductVendorDTO();
        BeanUtils.copyProperties(vendor, dto);
        BeanUtils.copyProperties(product, dto);
        BeanUtils.copyProperties(transaction, dto);
        return ServerResponseEntity.success(dto);
    }

    @DeleteMapping("/deleteTransactionById/{transactionId}")
    @Operation(summary = "删除一条交易订单" , description = "根据id删除交易订单")
    @Parameters({
            @Parameter(name = "transactionId", description = "交易订单id", in = ParameterIn.PATH)
    })
    public ServerResponseEntity<?> deleteTransactionById(
            @Valid @PathVariable("transactionId") Integer transactionId) {
        Transaction transaction = transactionService.getById(transactionId);
        if (transaction == null) {
            throw new MyBindException(ResponseEnum.TRANSACTION_NOT_FOUND);
        }
        if (StrUtil.equals(transaction.getStatus(), "1")) {
            throw new MyBindException(ResponseEnum.CANNOT_DELETE_TRANSACTION);
        }

        // 商品退回库存
        int amount = transaction.getAmount();
        int inventory = productService.getById(transaction.getProductId()).getInventory();
        productService.update(new LambdaUpdateWrapper<Product>().eq(Product::getProductId, transaction.getProductId())
                .set(Product::getInventory, amount + inventory));

        boolean success = transactionService.removeById(transactionId);
        return success ?
                ServerResponseEntity.success("删除成功") :
                ServerResponseEntity.success(ResponseEnum.DELETE_TRANSACTION_FAILED);
    }

    @PutMapping("/updateTransaction")
    @Operation(summary = "修改一条订单" , description = "修改一条订单，需传id和amount")
    public ServerResponseEntity<?> updateTransaction(
            @Valid @RequestBody UpdateTransactionDTO updateTransactionDTO) {
        Transaction transaction = transactionService.getById(updateTransactionDTO.getTransactionId());
        if (transaction == null) {
            throw new MyBindException(ResponseEnum.TRANSACTION_NOT_FOUND);
        }
        if (StrUtil.equals(transaction.getStatus(), "1")) {
            throw new MyBindException(ResponseEnum.CANNOT_UPDATE_TRANSACTION);
        }
        // 检查并更新库存，库存不足报错
        inspectInventory(transaction.getProductId(), updateTransactionDTO.getAmount());

        Transaction tran = new Transaction();
        BeanUtils.copyProperties(updateTransactionDTO, tran);
        boolean success = transactionService.updateById(tran);
        return success ?
                ServerResponseEntity.success("修改成功") :
                ServerResponseEntity.success(ResponseEnum.UPDATE_TRANSACTION_FAILED);
    }

    @PostMapping("/insertTransaction")
    @Operation(summary = "新增一条订单" , description = "新增一条订单")
    public ServerResponseEntity<?> insertTransaction(
            @Valid @RequestBody Transaction transaction) {
        // 检查并更新库存，库存不足报错
        inspectInventory(transaction.getProductId(), transaction.getAmount());

        transaction.setDateTime(new Date());
        Random random = new Random();
        int randomNumber = random.nextInt(2); // 生成0或1的随机数
        transaction.setStatus(String.valueOf(randomNumber));
        boolean success = transactionService.save(transaction);
        return success ?
                ServerResponseEntity.success("新增成功") :
                ServerResponseEntity.success(ResponseEnum.INSERT_TRANSACTION_FAILED);
    }

    @PostMapping("/insertBatchTransaction")
    @Operation(summary = "批量新增订单" , description = "批量新增订单")
    public ServerResponseEntity<?> insertBatchTransaction(
            @Valid @RequestBody List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            // 检查并更新库存，库存不足报错
            inspectInventory(transaction.getProductId(), transaction.getAmount());
        }
        Random random = new Random();
        transactions.forEach(transaction -> {
            transaction.setDateTime(new Date());
            int randomNumber = random.nextInt(2); // 生成0或1的随机数
            transaction.setStatus(String.valueOf(randomNumber));
        });
        boolean success = transactionService.saveBatch(transactions);
        return success ?
                ServerResponseEntity.success("批量新增成功") :
                ServerResponseEntity.success(ResponseEnum.INSERT_BATCH_TRANSACTION_FAILED);
    }

    /**
     * 检查并更新库存
     * @param productId 商品id
     * @param amount 需要的商品数量
     */
    private void inspectInventory(Integer productId, Integer amount) {
        Product product = productService.getById(productId);
        // 剩余库存数
        int inventory = product.getInventory();
        if (inventory < amount) {
            throw new MyBindException(ResponseEnum.INSERT_TRANSACTION_FAILED_NO_INVENTORY);
        }
        productService.update(new LambdaUpdateWrapper<Product>().eq(Product::getProductId, productId)
                .set(Product::getInventory, inventory - amount));
    }
}

