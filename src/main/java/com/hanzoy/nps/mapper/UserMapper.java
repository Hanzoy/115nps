package com.hanzoy.nps.mapper;

import com.hanzoy.nps.domain.User;

public interface UserMapper {
    User login(String username, String password);
}
