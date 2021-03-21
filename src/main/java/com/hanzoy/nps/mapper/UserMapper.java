package com.hanzoy.nps.mapper;


import com.hanzoy.nps.domain.User;
import com.hanzoy.nps.po.LoginPO;

public interface UserMapper {
    /**
     * 登陆
     * @param username 账号
     * @param password 密码
     * @return 登陆返回的结果
     */
    LoginPO login(String username, String password);

    /**
     * 注册
     * @param username 账号
     * @param password 密码
     * @param name 用户名
     * @return 注册的用户id
     */
    Integer register(String username, String password, String name);

    /**
     * 通过username查询User
     * @param username 账号
     * @return 查询的User
     */
    User searchUsername(String username);

    /**
     * 查询某个role_id对应的role_name
     * @param id role_id
     * @return role_name
     */
    String searchRole(int id);
}
