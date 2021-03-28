package com.hanzoy.nps.pojo.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ClientVO {
    /**
     * 公网ip
     */
    private static String ip;

    /**
     * 公网代理运行端口
     */
    private static String bridgePort;

    /**
     * 客户端集合
     */
    private ArrayList<Client> rows;

    @Data
    public static class Client{
        /**
         * 客户端id
         */
        private Integer id;

        /**
         * 创建者
         */
        private String creator;

        /**
         * 验证密钥
         */
        private String VerifyKey;

        /**
         * 客户端所在ip
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
         * 客户端连接状态
         */
        private Boolean IsConnect;
    }
}
