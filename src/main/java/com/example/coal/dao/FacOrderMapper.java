package com.example.coal.dao;

import com.example.coal.bean.FactoryOrder;

import java.util.List;
import java.util.Map;

public interface FacOrderMapper {


    /***
     * 查看可接受订单（司机角度）
     * @return 当前可接受列表（司机角度）
     */
    List<FactoryOrder> getWaitOrderList();

    /***
     * 根据工厂订单id，获得工厂订单的详情
     * @param facOrder_id 工厂订单id
     * @return 工厂订单信息
     */
    FactoryOrder getFacOrderInfo(int facOrder_id);

    /****
     * 新增工厂信息
     * @param factoryOrder 工厂订单实体
     * @return 1/0
     */
    int addNewFacOrder(FactoryOrder factoryOrder);

    /***
     * 修改工厂订单信息
     * @param factoryOrder 工厂订单实体
     * @return 1-0
     */
    int editFacOrder(FactoryOrder factoryOrder);

    /***
     * 获得历史订单（出）
     * @param fac_id 工厂订单id
     * @return 历史订单列表（出）
     * SQL语句：SELECT * from factory_order WHERE ff_id = #{fac_id} AND order_state = 0;
     */
    List<FactoryOrder> getFacOrderEd1(int fac_id);


    /***
     * 获得历史订单（入）
     * @param fac_id 工厂订单id
     * @return 历史订单列表（入）
     * SQL语句：SELECT * from factory_order WHERE ft_id = #{fac_id} AND order_state = 0;
     */
    List<FactoryOrder> getFacOrderEd2(int fac_id);


    /***
     * 获得等待被确认的订单（该工厂为卖家）
     * @param fac_id 工厂id
     * @return List<订单>
     * SQL语句：SELECT * from factory_order WHERE ff_id = #{fac_id} AND order_state = 2;
     */
    List<FactoryOrder> getFacOrderWait1(int fac_id);

    /***
     * 获得等待确认的订单（该工厂为买家）
     * @param fac_id 工厂id
     * @return List<订单>
     * SQL语句：SELECT * from factory_order WHERE ft_id = #{fac_id} AND order_state = 2;
     */
    List<FactoryOrder> getFacOrderWait2(int fac_id);

    /****
     * 获得正在进行中的订单（该工厂为卖家）
     * @param fac_id  工厂id
     * @return List<订单>
     */
    List<FactoryOrder> getFacOrderIng1(int fac_id);

    /****
     * 获得正在进行中的订单（该工厂为买家）
     * @param fac_id 工厂id
     * @return List<订单>
     */
    List<FactoryOrder> getFacOrderIng2(int fac_id);


}
