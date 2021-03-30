package com.hanzoy.nps.mapper;

import com.hanzoy.nps.pojo.po.TunnelPO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface TunnelMapper {
    /**
     * 关键字查询隧道
     * @param id 客户端id
     * @param keyword 搜索隧道关键字
     * @return 查询结果
     */
    ArrayList<TunnelPO> selectTunnel(@Param("id") Integer id, @Param("keyword") String keyword);

    /**
     * 插入数据
     * @param id 隧道id
     * @param client_id 客户端id
     * @param creator 创建者
     * @param remark 备注
     * @param port 端口
     * @param target 代理目标
     */
    void insertTunnel(@Param("id") Integer id,
                      @Param("client_id") Integer client_id,
                      @Param("creator") Integer creator,
                      @Param("remark") String remark,
                      @Param("port") String port,
                      @Param("target") String target);

    /**
     * 通过端口选择隧道
     * @param port 端口
     * @return 返回查询到到隧道
     */
    TunnelPO selectTunnelByPort(String port);

    /**
     * 删除隧道
     * @param id 隧道id
     */
    void deleteTunnel(String id);

    /**
     * 修改隧道
     * @param id 隧道id
     * @param remark 备注
     * @param target 代理目标
     */
    void updateTunnel(@Param("id") String id,
                      @Param("remark") String remark,
                      @Param("target") String target,
                      @Param("port") String port,
                      @Param("client_id") String clientId);
}
