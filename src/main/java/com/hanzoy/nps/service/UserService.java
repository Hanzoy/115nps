package com.hanzoy.nps.service;

import com.hanzoy.nps.dto.CommonResult;

public interface UserService {
    CommonResult login(String username, String password);
}