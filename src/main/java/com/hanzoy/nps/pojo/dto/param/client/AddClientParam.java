package com.hanzoy.nps.pojo.dto.param.client;

import lombok.Data;

@Data
public class AddClientParam {
    /**
     * 备注
     */
    private String remark;

    /**
     * 验证密钥 空则随机生成
     */
    private String key;

    /**
     * 用户token
     */
    private String token;
}
