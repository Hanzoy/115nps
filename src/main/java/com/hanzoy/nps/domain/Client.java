package com.hanzoy.nps.domain;

import lombok.Data;

@Data
public class Client {
    private Integer id;
    private Integer creator;
    private String remark;
    private String key;
}
