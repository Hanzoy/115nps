package com.hanzoy.nps.controller;

import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.dto.param.tunnel.AddTunnelParam;
import com.hanzoy.nps.pojo.dto.param.tunnel.DelTunnelParam;
import com.hanzoy.nps.pojo.dto.param.tunnel.EditTunnelParam;
import com.hanzoy.nps.pojo.dto.param.tunnel.GetTunnelParam;
import com.hanzoy.nps.service.NPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tunnel")
public class TunnelController {
    @Autowired
    NPSService npsService;

    @PostMapping("/list")
    public CommonResult getTunnel(@RequestBody GetTunnelParam param){
        return npsService.getTunnelList(param.getId(), param.getSearch(), param.getToken());
    }

    @PostMapping("/add")
    public CommonResult addTunnel(@RequestBody AddTunnelParam param){
        return npsService.addTunnel(param.getId(), param.getRemark(), param.getPort(), param.getTarget(), param.getToken());
    }

    @PostMapping("/del")
    public CommonResult delTunnel(@RequestBody DelTunnelParam param){
        return npsService.delTunnel(param.getId(), param.getToken());
    }

    @PostMapping("/edit")
    public CommonResult editTunnel(@RequestBody EditTunnelParam param){
        return npsService.editTunnel(param.getId(), param.getClient_id(), param.getRemark(), param.getPort(), param.getTarget(), param.getToken());
    }
}
