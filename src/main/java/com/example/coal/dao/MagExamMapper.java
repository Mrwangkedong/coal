package com.example.coal.dao;

import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryQualified;

import java.util.List;

public interface MagExamMapper {

    /**
     * 获得所有申请的工厂记录
     * @return List
     */
    List<FactoryMsg> getNewFacInfo();

    /**
     * 获得所有申请修改工厂资料的工厂信息
     * @return List
     */
    List<FactoryQualified> getEditFacInfo();

    /**
     * 向申请修改工厂信息表中添加新的记录
     * @param factoryQualified 待审核申请修改记录
     * @return 1/0
     */
    int addEditFacQualified(FactoryQualified factoryQualified);

    /**
     * 通过工厂id获得其待审核的资料
     * @param factory_id 工厂id
     * @return 待审核信息
     */
    FactoryQualified getEditFacQualifiedByFacId(int factory_id);


    /**
     * 获得全部待审核新司机申请
     * @return List
     */
    List<DriverMsg> getNewDriverList();

    /**
     * 获得全部待审批司机信息更改
     * @return List
     */
    List<DriverMsg> getEdirDriverList();
}
