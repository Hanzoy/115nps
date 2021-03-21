package com.hanzoy.nps.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Integer role;
}
