package com.example.coal.controller;


import com.example.coal.Utils.TimeUtils;
import com.example.coal.bean.DriverOrder;
import com.example.coal.bean.FactoryOrder;
import com.example.coal.server.DriverOrderServer;
import com.example.coal.server.FacOrderServer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*",maxAge = 3600)
@EnableSwagger2
public class DriverOrderController {
    @Autowired
    private DriverOrderServer driverOrderServer ;


//    查看具体司机的历史订单
    @ResponseBody
    @RequestMapping(path = "/getDriOrderList",method = RequestMethod.POST)
    @ApiOperation("获得司机的历史订单")
    public List<Map<String ,Object>> getDriOrderList(@ApiParam("输入一个司机id")@RequestParam int d_id){
        return driverOrderServer.getDriOrderList(d_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getOrderNow",method = RequestMethod.POST)
    @ApiOperation("获得司机的当前订单")
    public DriverOrder getOrderNow(@ApiParam("输入一个司机id")@RequestParam int d_id){
        return driverOrderServer.getOrderNow(d_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getOrderNowRoute",method = RequestMethod.POST)
    @ApiOperation("获得司机的当前订单的双方工厂位置")
    public Map<String, Float> getOrderNowRoute(@ApiParam("输入一个司机id")@RequestParam int d_id){
        return driverOrderServer.getOrderNowRoute(d_id);
    }


    @ApiOperation("通过工厂订单id，获得双方工厂名称,及订单货物单价,两厂家之间的距离")
    @ResponseBody
    @RequestMapping(path = "/getFacNames",method = RequestMethod.POST)
    public Map<String,Object> getFacNames(@ApiParam("输入一个工厂订单id")@RequestParam int factory_orderid){
        return driverOrderServer.getFacNames(factory_orderid);
    }


//    添加新的订单
    @ResponseBody
    @RequestMapping(path = "/addDriOrderInfo",method = RequestMethod.POST)
    @ApiOperation("添加新的订单")
    public int addDriOrderInfo(@ApiParam("输入一个司机id")@RequestParam int driver_id,
                              @ApiParam("输入一个工厂订单id")@RequestParam int factory_orderid){
        Map<String ,Object> map = new HashMap<>();
        map.put("driver_id",driver_id);
        map.put("factory_orderid",factory_orderid);
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        Timestamp order_startdate = new Timestamp(date.getTime());     //   讲日期时间转换为数据库中的timestamp类型
        map.put("order_startdate",order_startdate);
        return driverOrderServer.addDriOrderInfo(map);
    }

//    获得当前可接收订单
    @ResponseBody
    @ApiOperation("获得当前可接搜订单List")
    @RequestMapping(path = "/getOrderWaitInfo",method = RequestMethod.GET)
    public List<Map<String, Object>> getOrderWaitInfo(){
        return driverOrderServer.getOrderWaitInfo();
    }

    @ResponseBody
    @RequestMapping(path = "/getDriOrderInfo",method = RequestMethod.POST)
    @ApiOperation("获得司机订单的具体信息")
    public DriverOrder getDriOrderInfo(@ApiParam("输入一个订单id")@RequestParam int order_id){
        System.out.println(order_id);
        return driverOrderServer.getDriOrderInfo(order_id);
    }

    @ResponseBody
    @RequestMapping(path = "/editDriOrderArriveFFInfo",method = RequestMethod.POST)
    @ApiOperation(" 修改到卖家厂时间，操作员，皮重等等")
    public int editDriOrderArriveFFInfo(@RequestParam int order_id,
                                        @RequestParam int order_fedituserid1,@RequestParam float pz){
        return driverOrderServer.editDriOrderArriveFFInfo(order_id,order_fedituserid1,pz);
    }


    /***
     * Data 在在Java中注入
     */
    @ResponseBody
    @RequestMapping(path = "/editDriOrderLeaveFFInfo",method = RequestMethod.POST)
    @ApiOperation("修改离开卖家厂时间，操作员，离厂毛重等等")
    public int editDriOrderLeaveFFInfo(@RequestParam int order_id,@RequestParam int order_fedituserid2,@RequestParam float weightNow){

        return driverOrderServer.editDriOrderLeaveFFInfo(order_id,order_fedituserid2,weightNow);
    }

    @ResponseBody
    @RequestMapping(path = "/editDriOrderEndInfo",method = RequestMethod.POST)
    @ApiOperation("修改到达买家工厂（结束）时间，操作员，到厂毛重,是否准时，评价星级，状态")
    public int editDriOrderEndInfo(@RequestParam int order_id,
                                   @RequestParam int order_tedituserid,@RequestParam float weightNow,
                                   @RequestParam int order_star){
        //修改司机订单信息
    return driverOrderServer.editDriOrderEndInfo(order_id,order_tedituserid,weightNow,order_star);
    }

    @ResponseBody
    @RequestMapping(path = "/getDriOrderAllInfo",method = RequestMethod.POST)
    @ApiOperation("工厂方查看司机订单信息")
    public Map<String ,Object> getDriOrderAllInfo(int dri_orderId){
        return driverOrderServer.getDriOrderAll(dri_orderId);
    }

    @ResponseBody
    @RequestMapping(path = "/editJinWei",method = RequestMethod.POST)
    @ApiOperation("更改经纬度")
    public int editJinWei(int d_id , float order_longitude,float order_latitude){
        DriverOrder orderNow = driverOrderServer.getOrderNow(d_id);
        if (orderNow == null){
            return -1;
        }else {
            orderNow.setOrder_longitude(order_longitude);
            orderNow.setOrder_latitude(order_latitude);
            return driverOrderServer.editDriOrderInfo(orderNow);
        }
    }




}
