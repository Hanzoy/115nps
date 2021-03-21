package com.hanzoy.nps.dto.param.tunnel;

import lombok.Data;

@Data
public class EditTunnelParam {
    private String id;
    private String client_id;
    private String remark;
    private String port;
    private String target;
}
