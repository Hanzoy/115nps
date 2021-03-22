package com.hanzoy.nps.vo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ClientVO {
    private static String ip;
    private static String bridgePort;
    private ArrayList<Client> rows;

    @Data
    public static class Client{
        private Integer id;
        private String creator;
        private String VerifyKey;
        private String Addr;
        private String Remark;
        private Boolean Status;
        private Boolean IsConnect;
    }
}
