package com.hanzoy.nps.pojo.dto.param.tunnel;

import lombok.Data;

@Data
public class DelTunnelParam {
    /**
     * 待删除的隧道id
     */
    private String id;

    /**
     * 用户token
     */
    private String token;
}
