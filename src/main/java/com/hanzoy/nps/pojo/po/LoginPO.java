package com.hanzoy.nps.pojo.po;

import lombok.Data;

@Data
public class LoginPO {
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 权限
     */
    private String role;
}
