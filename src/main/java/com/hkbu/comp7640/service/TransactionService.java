package com.hkbu.comp7640.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.TransactionWithProductVendorDTO;
import com.hkbu.comp7640.entity.Transaction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
public interface TransactionService extends IService<Transaction> {
    /**
     * 分页查询建议订单
     * @param page 分页
     * @return
     */
    IPage<TransactionWithProductVendorDTO> pageTransaction(Page<TransactionWithProductVendorDTO> page, Long userId);

}
