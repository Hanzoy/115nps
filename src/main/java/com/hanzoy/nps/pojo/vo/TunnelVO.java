package com.hanzoy.nps.pojo.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TunnelVO {

    /**
     * 隧道集合
     */
    public ArrayList<Tunnel> rows;

    @Data
    public static class Tunnel{
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

        /**
         * 隧道状态
         */
        private String status;

        /**
         * 隧道连接状态
         */
        private String runStatus;
    }
}
