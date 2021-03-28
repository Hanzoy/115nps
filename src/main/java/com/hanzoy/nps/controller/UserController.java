package com.hanzoy.nps.controller;

import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.dto.param.user.RegisterParam;
import com.hanzoy.nps.pojo.dto.param.user.LoginParam;
import com.hanzoy.nps.pojo.dto.param.user.TokenLoginParam;
import com.hanzoy.nps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginParam param){
        return userService.login(param.getUsername(), param.getPassword());
    }

    @PostMapping("/tokenLogin")
    public CommonResult tokenLogin(@RequestBody TokenLoginParam param){
        return userService.login(param.getToken());
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterParam param){
        return userService.register(param.getUsername(), param.getPassword(), param.getName());
    }
}
