package com.hanzoy.nps.domain;

import lombok.Data;

@Data
public class Role {
    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String name;
}
