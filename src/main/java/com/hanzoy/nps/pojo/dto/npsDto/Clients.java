package com.hanzoy.nps.pojo.dto.npsDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Clients {
    /**
     * NPS运行端口
     */
    private String bridgePort;

    /**
     * NPS运行类别
     */
    private String bridgeType;

    /**
     * 公网ip
     */
    private String ip;

    /**
     * 客户端集合
     */
    private ArrayList<Client> rows;

    @Data
    public static class Client{
        /**
         * 客户端id
         */
        private Integer Id;

        /**
         * 验证密钥
         */
        private String VerifyKey;

        /**
         * 客户端地址
         */
        private String Addr;

        /**
         * 备注
         */
        private String Remark;

        /**
         * 客户端状态
         */
        private Boolean Status;

        /**
         * 连接状态
         */
        private Boolean IsConnect;
    }

}
