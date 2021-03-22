package com.hanzoy.nps.controller;

import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.dto.param.client.AddClientParam;
import com.hanzoy.nps.pojo.dto.param.client.ClientListParam;
import com.hanzoy.nps.pojo.dto.param.client.DelClientParam;
import com.hanzoy.nps.pojo.dto.param.client.EditClientParam;
import com.hanzoy.nps.service.NPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    NPSService npsService;

    @PostMapping("/list")
    public CommonResult clientList(@RequestBody ClientListParam param){
        return npsService.getClientList(param.getSearch(), param.getToken());
    }

    @PostMapping("/add")
    public CommonResult addClient(@RequestBody AddClientParam param){
        return npsService.addClient(param.getRemark(), param.getKey(), param.getToken());
    }

    @PostMapping("/del")
    public CommonResult delClient(@RequestBody DelClientParam param){
        return npsService.delClient(param.getId(), param.getToken());
    }

    @PostMapping("/edit")
    public CommonResult editClient(@RequestBody EditClientParam param){
        return npsService.editClient(param.getId(), param.getRemark(), param.getKey(), param.getToken());
    }
}
