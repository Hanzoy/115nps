package com.hanzoy.nps.service;

import com.hanzoy.nps.dto.CommonResult;

public interface UserService {
    /**
     * 用户登陆
     * @param username 账号
     * @param password 密码
     * @return 请求结果
     */
    CommonResult login(String username, String password);

    /**
     * 用户token登陆
     * @param token 用户token
     * @return 请求结果
     */
    CommonResult login(String token);

    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @param name 用户名
     * @return 请求结果
     */
    CommonResult register(String username, String password, String name);

    /**
     * 检查token
     * 调用该方法即可
     * @param token 带检查的token
     */
    void checkToken(String token);
}
