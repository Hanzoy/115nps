package com.hanzoy.nps.pojo.dto.param.user;

import lombok.Data;

@Data
public class ChangePasswordParam {
    private String token;
    private String oldPassword;
    private String newPassword;
}
