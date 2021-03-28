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

    void insertTunnel(@Param("id") Integer id,
                      @Param("client_id") Integer client_id,
                      @Param("creator") Integer creator,
                      @Param("remark") String remark,
                      @Param("port") String port,
                      @Param("target") String target);

    TunnelPO selectTunnelByPort(String port);

    void deleteTunnel(String port);
}
