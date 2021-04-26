package com.example.coal.server;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.dao.DriverOrderMapper;
import com.example.coal.dao.FacMsgMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.coal.server.DriverMsgServer.sqlsession;


public class FacMsgServer {

    FacMsgMapper mapper = sqlsession.getMapper(FacMsgMapper.class);

    /**
     * 根据工厂id，获得工厂信息
     * @param fac_id
     * @return
     */
    public FactoryMsg getFacInfo(int fac_id){
        return mapper.getFacInfo(fac_id);
    }

    /****
     * 根据工厂订单id获得双方工厂的name
     * @param fac_orederID 工厂订单id
     * @return 双方工厂名称
     */
    public Map<String,Object> getNamesByFacOrderId(int fac_orederID){
        String ffName = mapper.getFFNameByFacOrderID(fac_orederID);
        String ftName = mapper.getFTNameByFacOrderID(fac_orederID);
        Map<String,Object> map = new HashMap<>();
        map.put("ffName",ffName);
        map.put("ftName",ftName);

        return map;
    }


    /**
     * 获得全部工厂名称
     * @return 全部工厂名称
     */
    public List<String> getFacNames(){
        List<FactoryMsg> facInfos = mapper.getFacInfos();
        List<String> names = new ArrayList<>();
        for (FactoryMsg facInfo : facInfos) {
            names.add(facInfo.getName());
        }

        return names;
    }

    /***
     * 更改工厂信息
     * @param factoryMsg 工厂信息实体
     * @return 1/0
     */
    public int editFacMsg(FactoryMsg factoryMsg){
        int i = mapper.editFacMsg(factoryMsg);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

}
