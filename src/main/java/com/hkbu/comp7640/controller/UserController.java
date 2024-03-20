package com.hkbu.comp7640.controller;


import com.hkbu.comp7640.dto.UserRegisterDTO;
import com.hkbu.comp7640.entity.User;
import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hkbu
 * @since 2024-03-18
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    @Operation(summary = "用户注册", description = "用户注册")
    public ServerResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userRegisterDTO, user);
            boolean success = userService.save(user);
            return success ?
                    ServerResponseEntity.success("注册成功") :
                    ServerResponseEntity.success(ResponseEnum.REGISTER_USER_FAILED);
        } catch (DuplicateKeyException e) {
            return ServerResponseEntity.success(ResponseEnum.DUPLICATED_USER_NAME);
        }
    }

}

