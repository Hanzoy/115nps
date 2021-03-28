package com.hanzoy.nps.domain;

import lombok.Data;

@Data
public class Client {
    /**
     * 客户端id
     */
    private Integer id;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 连接密钥
     */
    private String key;
}
