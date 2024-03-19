package com.hkbu.comp7640.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkbu.comp7640.dto.TransactionWithProductVendorDTO;
import com.hkbu.comp7640.entity.Transaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {

    IPage<TransactionWithProductVendorDTO> pageTransaction(Page<TransactionWithProductVendorDTO> page, @Param("userId") Long userId);

}
