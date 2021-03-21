package com.hanzoy.nps.dto.param;

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
}
