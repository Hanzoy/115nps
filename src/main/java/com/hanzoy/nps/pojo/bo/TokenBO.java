package com.hanzoy.nps.pojo.bo;

import lombok.Data;

@Data
public class TokenBO {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户身份
     */
    private String role;
}
