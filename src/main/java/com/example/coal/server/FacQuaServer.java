package com.example.coal.server;

import com.example.coal.bean.FactoryQualified;
import com.example.coal.dao.FacQualifiedMapper;

import static com.example.coal.server.DriverMsgServer.sqlsession;

public class FacQuaServer {

    FacQualifiedMapper mapper = sqlsession.getMapper(FacQualifiedMapper.class);

    /**
     * 更具工厂id，获得其在待审核表中的信息
     * @param fac_id 工厂id
     * @return 审核信息实体
     */
    public FactoryQualified getFacQualifiedInfo(int fac_id){
        return mapper.getFacQualifiedInfo(fac_id);
    }

    /**
     * 查看数据表中是否存在当前数据，如果存在返回；如果不存在就新建一个返回
     * @param fac_id
     * @return
     */
    public FactoryQualified exitFacQuaByFacId(int fac_id){
        //先进行查看，当前用户是否在Qualified表中存在
        FactoryQualified facQualifiedInfo = getFacQualifiedInfo(fac_id);
        //如果不存在，就新建
        if (facQualifiedInfo == null){
            addFacQualified(fac_id);
            FactoryQualified facQualifiedInfoRes = new FactoryQualified();
            facQualifiedInfoRes.setFactory_id(fac_id);
            return facQualifiedInfoRes;
        }else {
            //如果存在就返回查到的实体
            return facQualifiedInfo;
        }
    }

    /**
     * 新建一个工厂信息审核
     * @param fac_id 工厂id
     * @return 1/0
     */
    public int addFacQualified(int fac_id){
        int i = mapper.addFacQualified(fac_id);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

    /***
     * 更改待审核工厂信息实体
     * @param factoryQualified 审核工厂信息实体
     * @return 1/0
     */
    public int editFacQualified(FactoryQualified factoryQualified){

        int i = mapper.editFacQualified(factoryQualified);
        if (i == 1){
            sqlsession.commit();
        }
        return i;

    }

}
