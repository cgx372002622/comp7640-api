package com.hkbu.comp7640.dao;

import com.hkbu.comp7640.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
