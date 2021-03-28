package com.hanzoy.nps.pojo.dto.param.user;

import lombok.Data;

@Data
public class RegisterParam {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String name;
}
