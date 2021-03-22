package com.hanzoy.nps.mapper;


import com.hanzoy.nps.po.ClientPO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ClientMapper {
    ArrayList<ClientPO> selectAllClient();
    ArrayList<ClientPO> selectClient(@Param("keyword") String keyword);
}
