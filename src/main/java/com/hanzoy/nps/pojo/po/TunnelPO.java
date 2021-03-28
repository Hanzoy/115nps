package com.hanzoy.nps.pojo.po;

import lombok.Data;

@Data
public class TunnelPO {
    /**
     * 隧道id
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
     * 公网端口
     */
    private String port;

    /**
     * 代理目标
     */
    private String target;
}
