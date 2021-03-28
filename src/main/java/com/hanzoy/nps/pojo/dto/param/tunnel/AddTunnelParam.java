package com.hanzoy.nps.pojo.dto.param.tunnel;

import lombok.Data;

@Data
public class AddTunnelParam {
    /**
     * 添加的隧道id
     */
    private String id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 代理目标
     */
    private String target;

    /**
     * 代理后端口
     */
    private String port;

    /**
     * 用户token
     */
    private String token;
}
