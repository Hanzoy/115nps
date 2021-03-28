package com.hanzoy.nps.pojo.po;

import lombok.Data;

@Data
public class ClientPO {
    /**
     * 客户端id
     */
    private Integer id;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 访问密钥
     */
    private String key;
}
