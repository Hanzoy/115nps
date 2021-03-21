package com.hanzoy.nps.dto.npsDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Clients {
    private String bridgePort;
    private String bridgeType;
    private String ip;
    private ArrayList<Client> rows;

    @Data
    public static class Client{
        private String Id;
        private String VerifyKey;
        private String Addr;
        private String Remark;
        private Boolean Status;
        private Boolean IsConnect;
    }

}
