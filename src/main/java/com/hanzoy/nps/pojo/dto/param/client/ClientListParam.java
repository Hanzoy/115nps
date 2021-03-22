package com.hanzoy.nps.pojo.dto.param.client;

import lombok.Data;

@Data
public class ClientListParam {
    /**
     * 关键字搜索 空表示全搜索
     */
    private String search;

    /**
     * 用户token
     */
    private String token;
}
