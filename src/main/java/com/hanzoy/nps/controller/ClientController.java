package com.hanzoy.nps.controller;

import com.hanzoy.nps.dto.CommonResult;
import com.hanzoy.nps.dto.param.AddClientParam;
import com.hanzoy.nps.dto.param.ClientListParam;
import com.hanzoy.nps.dto.param.DelClientParam;
import com.hanzoy.nps.dto.param.EditClientParam;
import com.hanzoy.nps.service.impl.NPSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    NPSServiceImpl NPSService;

    @PostMapping("/list")
    public CommonResult clientList(@RequestBody ClientListParam param){
        return NPSService.getClientList(param.getSearch());
    }

    @PostMapping("/add")
    public CommonResult addClient(@RequestBody AddClientParam param){
        return NPSService.addClient(param.getRemark(), param.getKey());
    }

    @PostMapping("/del")
    public CommonResult delClient(@RequestBody DelClientParam param){
        return NPSService.delClient(param.getId());
    }

    @PostMapping("/edit")
    public CommonResult editClient(@RequestBody EditClientParam param){
        return NPSService.editClient(param.getId(), param.getRemark(), param.getKey());
    }
}