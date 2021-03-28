package com.hanzoy.nps.service;

import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.dto.npsDto.Clients;
import com.hanzoy.nps.pojo.dto.npsDto.Tunnels;

public interface NPSService {
    /**
     * 获取当前管理的有效Cookie
     * @return cookie
     */
    String getCOOKIE_beegosessionID();

    /**
     * 获取client列表
     * @param search 搜索的关键字
     * @return client列表
     */
    Clients getClientList(String search);

    /**
     * 获取client列表
     * @param search 搜索的关键字
     * @param token 用户传入的token
     * @return client列表
     */
    CommonResult getClientList(String search, String token);

    /**
     * 新增一个client
     * @param remark 备注
     * @param vkey 新增client的key值
     * @param token 用户token
     * @return 新增结果
     */
    CommonResult addClient(String remark, String vkey, String token);

    /**
     * 查看某一个client的隧道
     * @param id client Id
     * @param search 搜索的关键字
     * @return 查询结果
     */
    Tunnels getTunnel(String id, String search);

    /**
     * 查看某一个client的隧道
     * @param id client Id
     * @param search 搜索的关键字
     * @param token 用户token
     * @return 查询结果
     */
    CommonResult getTunnel(String id, String search, String token);

    /**
     * 删除某个客户端
     * @param id client Id
     * @param token 用户token
     * @return 返回删除结果
     */
    CommonResult delClient(String id, String token);

    /**
     * 修改某个客户端
     * @param id 客户端id
     * @param remark 修改的备注
     * @param key 修改的密钥
     * @param token 用户token
     * @return 请求结果
     */
    CommonResult editClient(String id, String remark, String key, String token);

    /**
     * 新建隧道
     * @param id 客户端ID
     * @param remark 备注
     * @param tunnelPort 隧道端口
     * @param target 代理目标
     * @param token 用户token
     * @return 请求结果
     */
    CommonResult addTunnel(String id, String remark, String tunnelPort, String target, String token);

    /**
     * 删除隧道
     * @param id 需要删除的隧道id
     * @param token 用户token
     * @return 删除结果
     */
    CommonResult delTunnel(String id, String token);

    /**
     * 修改隧道
     * @param client_id 客户端id
     * @param id 隧道id
     * @param remark 备注
     * @param tunnelPort 隧道端口
     * @param target 代理目标
     * @return 请求结果
     */
    CommonResult editTunnel(String client_id, String id, String remark, String tunnelPort, String target, String token);
}
