package com.hanzoy.nps.dto.param;

import lombok.Data;

@Data
public class AddTunnelParam {
    private String id;
    private String remark;
    private String target;
    private String port;
}
