package com.hanzoy.nps.service.impl;

import com.hanzoy.nps.domain.User;
import com.hanzoy.nps.dto.CommonResult;
import com.hanzoy.nps.mapper.UserMapper;
import com.hanzoy.nps.po.LoginPO;
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
        //用户登陆
        LoginPO loginPO = userMapper.login(username, MD5Utils.MD5(password));

        if(loginPO == null)
            return CommonResult.fail("A0200", "用户登陆失败");

        //写入token
        String token = jwtUtils.createTokenCustomFields(loginPO, "id", "name", "role");
        //创建返回体data
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", loginPO.getName());
        data.put("role", loginPO.getRole());
        data.put("token", token);

        return CommonResult.success(data);
    }

    @Override
    public CommonResult login(String token) {
        //token验签
        if (!jwtUtils.checkToken(token))
            return CommonResult.fail("A0200", "用户登陆失败");
        //写入data
        HashMap<String, Object> data = (HashMap<String, Object>) jwtUtils.getBeanAsMap(token);
        data.remove("id");
        data.put("token", token);
        return CommonResult.success(data);
    }

    @Override
    public CommonResult register(String username, String password, String name) {
        //查看账号是否重复
        User user = userMapper.searchUsername(username);
        if(user != null)
            return CommonResult.fail("A0111", "用户名已存在");

        //注册账号
        Integer id = userMapper.register(username, MD5Utils.MD5(password), name);

        //获取roleName
        String roleName = userMapper.searchRole(1);

        //生成token
        HashMap<String, String> createToken = new HashMap<>();
        createToken.put("id", id.toString());
        createToken.put("name", name);
        createToken.put("role", roleName);
        String token = jwtUtils.createTokenFromMap(createToken);

        //写入data
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("role", roleName);
        data.put("token", token);
        return CommonResult.success(token);
    }
}
