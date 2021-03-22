package com.hanzoy.nps.service.impl;

import com.hanzoy.nps.domain.User;
import com.hanzoy.nps.pojo.bo.TokenBO;
import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.exception.CustomErrorException;
import com.hanzoy.nps.mapper.UserMapper;
import com.hanzoy.nps.pojo.po.LoginPO;
import com.hanzoy.nps.service.UserService;
import com.hanzoy.nps.utils.MD5Utils;
import com.hanzoy.utils.JWTUtils;
import com.hanzoy.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
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
        userMapper.register(username, MD5Utils.MD5(password), name);
        user = userMapper.searchUsername(username);

        //获取roleName
        String roleName = userMapper.searchRole(1);

        //生成token
        HashMap<String, Object> createToken = new HashMap<>();
        createToken.put("id", user.getId());
        createToken.put("name", user.getName());
        createToken.put("role", roleName);
        String token = jwtUtils.createTokenFromMap(createToken);

        //写入data
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("role", roleName);
        data.put("token", token);
        return CommonResult.success(data);
    }

    public void checkToken(String token){
        if(!jwtUtils.checkToken(token))
            throw new CustomErrorException("A0220", "用户token校验异常");
    }

    @Override
    public TokenBO getTokenInfo(String token) {
        TokenBO tokenBO = new TokenBO();
        HashMap<String, String> map = (HashMap<String, String>)jwtUtils.getBeanAsMap(token, String.class);
        tokenBO.setId(new Integer(map.get("id")));
        tokenBO.setName(map.get("name"));
        tokenBO.setRole(map.get("role"));
        return tokenBO;
    }
}
