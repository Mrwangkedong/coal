package com.example.coal.dao;


import com.example.coal.bean.DriverOrder;
import com.example.coal.bean.FactoryOrder;

import java.util.List;
import java.util.Map;

/***
 * 操作关于司机的订单信息
 */
public interface DriverOrderMapper {
//    根据司机id查看所有历史订单（仅显示时间，工厂双方，订单金额）
    List<DriverOrder> getDriOrderList(int d_id);
//    获得当前可接受订单
    List<FactoryOrder> getOrderWaitInfo();
//    查看司机的当前订单
    DriverOrder getOrderNow(int d_id);
//    根据订单id查找ff_name
    String getDriOrderFF_NAME(int dOrder_id);
//    根据订单id查找ft_name
    String getDriOrderFT_NAME(int dOrder_id);
    //    根据司机订单id查看订单的详细情况（当前/历史）
    DriverOrder getDriOrderInfo(int driOrder_id);
//    添加新的司机订单（司机id，工厂订单）{司机id，工厂订单id，保证金额}
    int addDriOrderInfo(Map<String ,Object> map);
//    修改司机进行中订单信息（到/离厂时间，操作员等等）
    int editDriOrderInfo(DriverOrder driverOrder);

    /***
     * 获得工厂订单的全部子订单【司机订单】
     * @param fac_orderID
     * @return
     */
    List<DriverOrder> getFacOrderSonOrder(int fac_orderID);
}
