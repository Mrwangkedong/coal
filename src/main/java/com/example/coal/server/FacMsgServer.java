package com.example.coal.server;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.dao.DriverOrderMapper;
import com.example.coal.dao.FacMsgMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.coal.server.DriverMsgServer.sqlsession;

@Service
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

    /**
     * 根据工厂id，获得工厂名称
     * @param fac_id 工厂id
     * @return 工厂名称
     */
    public String getFacNameById(int fac_id){
        FactoryMsg facInfo = getFacInfo(fac_id);
        return facInfo.getName();
    }

    /**
     * 增加新的工厂
     * @param facName 工厂名称
     * @param factory_lpname 工厂法人姓名
     * @param factory_lpcardnum 工厂法人身份证
     * @param factory_longitude 工厂经度
     * @param factory_latitude 工厂纬度
     * @param factory_address 工厂地址
     * @return 新建数据id   0-->已存在  2-->添加失败
     */
    public int addFacInfo(String facName,String factory_lpname,String factory_lpcardnum,
                          float factory_longitude,float factory_latitude,String factory_address){
//        根据工厂名称和法人查，是否已存在工厂
        FactoryMsg factoryMsg1 = mapper.exitFac(factory_lpname, facName);
        if (factoryMsg1 != null){
            return 0;
        }
        //新建一个FacMsg
        FactoryMsg factoryMsg = new FactoryMsg();
        factoryMsg.setName(facName);
        factoryMsg.setFactory_lpname(factory_lpname);
        factoryMsg.setFactory_lpcardnum(factory_lpcardnum);
        factoryMsg.setFactory_longitude(factory_longitude);
        factoryMsg.setFactory_latitude(factory_latitude);
        factoryMsg.setFactory_address(factory_address);
        factoryMsg.setFactory_ifpass(2);  //2表示申请中
        int i = mapper.addFacInfo(factoryMsg);
        if (i==1) {
            sqlsession.commit();
            factoryMsg1 = mapper.exitFac(factory_lpname, facName);
            return factoryMsg1.getId();
        }else
            return 2;

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
    public List<Map<String,Object>> getFacNames(){
        List<FactoryMsg> facInfos = mapper.getFacInfos();
        List<Map<String,Object>> names = new ArrayList<>();

        for (FactoryMsg facInfo : facInfos) {
            Map<String,Object> map = new HashMap<>();
            map.put("fac_id",facInfo.getId());
            map.put("fac_name",facInfo.getName());
            names.add(map);
        }

        return names;
    }

    /**
     * 获得全部工厂信息
     * @return List
     */
    public List<FactoryMsg> getAllFac(){
        return mapper.getAllFac();
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

    /**
     * 修改工厂信息的通过状态
     * @param fac_id
     * @param if_pass
     * @return
     */
    public int editFacIfPass(int fac_id,int if_pass){
        FactoryMsg facInfo = getFacInfo(fac_id);
        facInfo.setFactory_ifpass(if_pass);
        int i = mapper.editFacMsg(facInfo);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

}
