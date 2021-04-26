package com.example.coal.dao;

import com.example.coal.bean.FactoryQualified;

public interface FacQualifiedMapper {

    /**
     * 更具工厂id，获得其在待审核表中的信息
     * @param fac_id 工厂id
     * @return 审核信息实体
     */
    FactoryQualified getFacQualifiedInfo(int fac_id);

    /**
     * 新建一个工厂信息审核
     * @param fac_id 工厂id
     * @return 1/0
     */
    int addFacQualified(int fac_id);

    /***
     * 更改待审核工厂信息实体
     * @param factoryQualified 审核工厂信息实体
     * @return 1/0
     */
    int editFacQualified(FactoryQualified factoryQualified);

}
