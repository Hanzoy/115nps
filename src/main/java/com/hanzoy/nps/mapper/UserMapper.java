package com.hanzoy.nps.mapper;


import com.hanzoy.nps.po.LoginPO;

public interface UserMapper {
    LoginPO login(String username, String password);
}
