package com.hanzoy.nps.pojo.dto.param.user;

import lombok.Data;

@Data
public class LoginParam {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
