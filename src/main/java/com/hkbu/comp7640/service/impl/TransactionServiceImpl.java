package com.hkbu.comp7640.service.impl;

import com.hkbu.comp7640.entity.Transaction;
import com.hkbu.comp7640.dao.TransactionMapper;
import com.hkbu.comp7640.service.TransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
