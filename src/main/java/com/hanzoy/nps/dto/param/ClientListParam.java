package com.hanzoy.nps.dto.param;

import lombok.Data;

@Data
public class ClientListParam {
    /**
     * 关键字搜索 空表示全搜索
     */
    private String search;
}
