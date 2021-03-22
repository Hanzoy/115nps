package com.hanzoy.nps.pojo.dto.param.client;

import lombok.Data;

@Data
public class DelClientParam {
    /**
     * 删除的客户端id
     */
    private String id;

    /**
     * 用户的token
     */
    private String token;
}
