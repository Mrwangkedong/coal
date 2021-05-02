package com.example.coal.bean;


import com.example.coal.controller.FacOrderController;
import com.example.coal.dao.FacOrderMapper;
import com.example.coal.server.FacOrderServer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FactoryOrder{
    private int id;
//    卖方工厂id
    private int ff_id;
//    买方工厂id
    private int ft_id;
//    订单开始时间
    private Date order_startdate;
//    订单结束时间（所有子订单结束）
    private Date order_enddate;
//    订单结束接单时间
    private Date order_endupdate;
//    目标车辆数
    private int order_targetcarnum;
//    实际车辆数
    private int order_actualcarnum;
//    规定车辆类型
    private int order_carclass;
//    订单转态0/1/2:0表示结束；1表示接单/进行中；2表示待确认中；3表示接单完成进行中；4表示拒绝
    private int order_state;
//    针对订单拒绝理由
    private String order_refuseReason;
//    目标煤炭总重量
    private float order_targetweight;
//    实际煤炭总重量（离ff厂总重量）
    private float order_actualweight;
//    实际煤炭总重量（到ft厂总重量）
    private float order_actualweight2;
//    卖方工厂发起员工id
    private int order_fstartuserid;
//    规定运输时间！！！
    private int order_transporttime;
//    煤炭类别
    private String order_goodclass;
//    货物每吨运费
    private float order_goodprice;


}
