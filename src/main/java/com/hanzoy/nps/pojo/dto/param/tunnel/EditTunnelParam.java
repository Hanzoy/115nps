package com.hanzoy.nps.pojo.dto.param.tunnel;

import lombok.Data;

@Data
public class EditTunnelParam {
    /**
     * 修改的隧道id
     */
    private String id;

    /**
     * 隧道所属客户端
     */
    private String client_id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 公网端口
     */
    private String port;

    /**
     * 代理目标
     */
    private String target;

    /**
     * 用户token
     */
    private String token;
}
