package com.hkbu.comp7640.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.TransactionWithProductVendorDTO;
import com.hkbu.comp7640.entity.Transaction;
import com.hkbu.comp7640.dao.TransactionMapper;
import com.hkbu.comp7640.service.TransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public IPage<TransactionWithProductVendorDTO> pageTransaction(Page<TransactionWithProductVendorDTO> page) {
        return transactionMapper.pageTransaction(page);
    }

}
