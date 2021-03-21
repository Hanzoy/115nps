package com.hanzoy.nps.service;

import com.hanzoy.nps.dto.CommonResult;
import okhttp3.FormBody;
import okhttp3.Request;

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
    CommonResult getClientList(String search);

    /**
     * 新增一个client
     * @param remark 备注
     * @param vkey 新增client的key值
     * @return 新增结果
     */
    CommonResult addClient(String remark, String vkey);

    /**
     * 查看某一个client的隧道
     * @param id client Id
     * @param search 搜索的关键字
     * @return 查询结果
     */
    CommonResult getTunnel(String id, String search);

    /**
     * 删除某个客户端
     * @param id client Id
     * @return 返回删除结果
     */
    CommonResult delClient(String id);

    /**
     * 修改某个客户端
     * @param id 客户端id
     * @param remark 修改的备注
     * @param key 修改的密钥
     * @return 请求结果
     */
    CommonResult editClient(String id, String remark, String key);

    /**
     * 新建隧道
     * @param id 客户端ID
     * @param remark 备注
     * @param tunnelPort 隧道端口
     * @param target 代理目标
     * @return 请求结果
     */
    CommonResult addTunnel(String id, String remark, String tunnelPort, String target);

    /**
     * 删除隧道
     * @param id 需要删除的隧道id
     * @return 删除结果
     */
    CommonResult delTunnel(String id);

    CommonResult editTunnel(String client_id, String id, String remark, String tunnelPort, String target);
}
