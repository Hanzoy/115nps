package com.hanzoy.nps.mapper;


import com.hanzoy.nps.domain.Client;
import com.hanzoy.nps.pojo.bo.ClientBO;
import com.hanzoy.nps.pojo.po.ClientPO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ClientMapper {
    /**
     * 查询所有客户端
     * @return 客户端列表
     */
    ArrayList<ClientPO> selectAllClient();

    /**
     * 关键字查询客户端
     * @param keyword 搜索的关键字
     * @return 客户端列表
     */
    ArrayList<ClientPO> selectClient(@Param("keyword") String keyword);

    /**
     * 通过key查找客户端id
     * @param key 客户端key
     * @return 返回客户端id
     */
    Integer selectClientIdByKey(@Param("key") String key);

    /**
     * 向数据库插入客户端
     * @param client 待插入的数据库数据
     */
    void insertClient(Client client);

    /**
     * 删除指定id的客户端
     * @param id 删除的客户端id
     */
    void deleteClient(Integer id);

    /**
     * 更新客户端
     * @param clientBO 需要更新的客户端
     */
    void updateClient(ClientBO clientBO);
}
