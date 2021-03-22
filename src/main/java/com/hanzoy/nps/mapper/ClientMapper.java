package com.hanzoy.nps.mapper;


import com.hanzoy.nps.domain.Client;
import com.hanzoy.nps.pojo.po.ClientPO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ClientMapper {
    ArrayList<ClientPO> selectAllClient();
    ArrayList<ClientPO> selectClient(@Param("keyword") String keyword);
    Integer selectClientIdByKey(@Param("key") String key);
    void insertClient(Client client);
    void deleteClient(Integer id);
}
