package com.hanzoy.nps.pojo.dto.param.client;

import lombok.Data;

@Data
public class EditClientParam {
    /**
     * 需要修改的客户端id
     */
    private String id;

    /**
     * 需要修改的备注
     */
    private String remark;

    /**
     * 需要修改的密钥
     */
    private String key;
}
