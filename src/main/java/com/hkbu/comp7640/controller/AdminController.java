package com.hkbu.comp7640.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hkbu.comp7640.dto.UserLoginDTO;
import com.hkbu.comp7640.dto.VendorLoginDTO;
import com.hkbu.comp7640.entity.User;
import com.hkbu.comp7640.entity.Vendor;
import com.hkbu.comp7640.exception.UnAuthorizationException;
import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import com.hkbu.comp7640.service.UserService;
import com.hkbu.comp7640.service.VendorService;
import com.hkbu.comp7640.vo.UserVO;
import com.hkbu.comp7640.vo.VendorVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户/商户权限管理")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private VendorService vendorService;

    @Operation(method = "POST", summary = "用户登录", description = "用户登录，userName、password明文传输")
    @PostMapping("/user/login")
    public ServerResponseEntity<UserVO> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userLoginDTO.getUsername()));
        if (user == null) {
            throw new UnAuthorizationException(ResponseEnum.UNKNOWN_USER);
        }
        String password = userLoginDTO.getPassword();
        if (!StrUtil.equals(password, user.getPassword())) {
            throw new UnAuthorizationException(ResponseEnum.INVALID_PASSWORD);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ServerResponseEntity.success(userVO);
    }

    @Operation(method = "POST", summary = "商户登录", description = "商户登录，vendorName、password明文传输")
    @PostMapping("/vendor/login")
    public ServerResponseEntity<VendorVO> vendorLogin(@Valid @RequestBody VendorLoginDTO vendorLoginDTO) {

        Vendor vendor = vendorService.getOne(new LambdaQueryWrapper<Vendor>().eq(Vendor::getVendorName, vendorLoginDTO.getVendorName()));
        if (vendor == null) {
            throw new UnAuthorizationException(ResponseEnum.UNKNOWN_VENDOR);
        }
        String password = vendorLoginDTO.getPassword();
        if (!StrUtil.equals(password, vendor.getPassword())) {
            throw new UnAuthorizationException(ResponseEnum.INVALID_PASSWORD);
        }
        VendorVO vendorVO = new VendorVO();
        BeanUtils.copyProperties(vendor, vendorVO);
        return ServerResponseEntity.success(vendorVO);
    }

}
