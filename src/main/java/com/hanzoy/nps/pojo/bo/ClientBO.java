package com.hanzoy.nps.pojo.bo;

import lombok.Data;

@Data
public class ClientBO {
    /**
     * 客户端id
     */
    private Integer id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 隧道密钥
     */
    private String key;
}
