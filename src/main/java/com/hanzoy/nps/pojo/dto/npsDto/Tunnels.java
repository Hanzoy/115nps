package com.hanzoy.nps.pojo.dto.npsDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Tunnels {
    /**
     * 隧道集合
     */
    private ArrayList<Tunnel> rows;

    @Data
    public static class Tunnel{
        /**
         * 隧道id
         */
        private Integer id;

        /**
         * 备注
         */
        private String remark;

        /**
         * 公网端口
         */
        private String port;

        /**
         * 隧道状态
         */
        private String status;

        /**
         * 运行状态
         */
        private String runStatus;

        /**
         * 代理目标
         */
        private String target;
    }
}
