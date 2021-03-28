package com.hanzoy.nps.pojo.dto.param.tunnel;

import lombok.Data;

@Data
public class GetTunnelParam {
    /**
     * 查询的客户端id
     */
    private String id;

    /**
     * 查询的关键字
     */
    private String search;

    /**
     * 用户token
     */
    private String token;
}
