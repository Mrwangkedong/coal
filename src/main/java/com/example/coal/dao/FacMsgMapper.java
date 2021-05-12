package com.example.coal.dao;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryOrder;

import java.util.List;

public interface FacMsgMapper {

    /**
     * 添加新的工厂
     * @param factoryMsg 工厂信息实体
     * @return 1/0
     */
    int addFacInfo(FactoryMsg factoryMsg);

    /**
     * 根据法人姓名和工厂名称，查看是否已经存在
     * @param lpName 法人姓名
     * @param facName 工厂名称
     * @return
     */
    FactoryMsg exitFac(String lpName,String facName);

    /***
     * 获得工厂的具体信息
     * @param fac_id 工厂id
     * @return 工厂实体
     */
    FactoryMsg getFacInfo(int fac_id);

    /***
     * 更具工厂订单id，获得卖家工厂name
     * @param fac_orderID 工厂订单id
     * @return 卖家工厂name
     * SQL语句：SELECT factory_msg.`name` FROM factory_msg WHERE id=(SELECT factory_order.ff_id FROM factory_order WHERE id = 1)
     */
    String getFFNameByFacOrderID(int fac_orderID);

    /****
     * 更具工厂订单id，获得买家工厂name
     * @param fac_orderID 工厂订单id
     * @return 买家家工厂name
     * SQL语句：SELECT factory_msg.`name` FROM factory_msg WHERE id=(SELECT factory_order.ft_id FROM factory_order WHERE id = 1)
     */
    String getFTNameByFacOrderID(int fac_orderID);

    /***
     * 获得所有的工厂名称
     * @return 工厂名LIST
     */
    List<FactoryMsg> getFacInfos();

    /***
     * 更改工厂信息
     * @param factoryMsg 工厂信息实体
     * @return 1/0
     */
    int editFacMsg(FactoryMsg factoryMsg);

    /**
     * 获得全部工厂信息
     * @return List
     */
    List<FactoryMsg> getAllFac();
}
