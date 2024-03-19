package com.hkbu.comp7640.service.impl;

import com.hkbu.comp7640.entity.User;
import com.hkbu.comp7640.dao.UserMapper;
import com.hkbu.comp7640.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
