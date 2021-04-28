package com.example.coal.bean;


import lombok.Data;

import java.util.Date;

/***
 * 司机订单信息（factory_order）
 */
@Data
public class DriverOrder {
    private int id;

//    对应司机id
    private int driver_id;
//    对应工厂订单id
    private int factory_orderid;
//    订单开始时间（接单时间）
    private Date order_startdate;
//    到达卖家工厂时间/订单开始时间
    private Date order_arriveffactorydate;
//    离开卖家工厂时间
    private Date order_leaveffactorydate;
//    到达买家工厂时间/订单结束时间
    private Date order_enddate;
//    皮重
    private float order_pz;
//    毛重1（离开卖方工厂）
    private float order_mz;
//    毛重2（到达买方工厂）
    private float order_mz2;
//    订单是否准时
    private int order_ontime;
//    订单总金额
    private float order_money;
//    订单状态：1表示正在进行；0表示结束
    private int order_state;
//    保证金金额
    private float order_ensuremoney;
//    卖方过空车操作员id
    private int order_fedituserid1;
//    卖方过重车操作员id
    private int order_fedituserid2;
//    买方过重车操作员id
    private int order_tedituserid;
//    评价星级
    private int order_srart;
//    当前位置经度
    private float order_longitude;
//    当前位置纬度
    private float order_latitude;

}
