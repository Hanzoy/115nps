package com.hanzoy.nps.service.impl;

import com.hanzoy.nps.domain.User;
import com.hanzoy.nps.dto.CommonResult;
import com.hanzoy.nps.mapper.UserMapper;
import com.hanzoy.nps.service.UserService;
import com.hanzoy.nps.utils.MD5Utils;
import com.hanzoy.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Autowired
    JWTUtils jwtUtils;
    @Override
    public CommonResult login(String username, String password) {

        User loginUser = userMapper.login(username, MD5Utils.MD5(password));
        if(loginUser == null)
            return CommonResult.fail("A0200", "用户登陆失败");
        String token = jwtUtils.createTokenCustomFields(loginUser, "id", "name", "role");
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);
        return CommonResult.success(data);
    }
}
