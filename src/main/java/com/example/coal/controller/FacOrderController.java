package com.example.coal.controller;


import com.example.coal.Utils.TimeUtils;
import com.example.coal.bean.FactoryOrder;
import com.example.coal.server.FacOrderServer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*",maxAge = 3600)
@EnableSwagger2
public class FacOrderController {
    FacOrderServer facOrderServer = new FacOrderServer();

    @ResponseBody
    @RequestMapping(path = "/getFacOrderInfo",method = RequestMethod.POST)
    @ApiOperation("得到一个工厂订单的具体信息...")
    public Map<String,Object> getFacOrderInfo(@ApiParam("输入一个工厂订单id")@RequestParam int fac_orderid){
        return facOrderServer.getFacOrderInfo(fac_orderid);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderGPS",method = RequestMethod.POST)
    @ApiOperation("得到一个工厂订单的GPS信息...")
    Map<String,Object> getFacOrderGPS(int fac_orderID){
        return facOrderServer.getFacOrderGPS(fac_orderID);
    }


    @ResponseBody
    @RequestMapping(path = "/getFacOrderEd1",method = RequestMethod.POST)
    @ApiOperation("获得工厂历史订单（出）")
    List<Map<String,Object>> getFacOrderEd1(int fac_id){
        return facOrderServer.getFacOrderEd1(fac_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderEd2",method = RequestMethod.POST)
    @ApiOperation("获得工厂历史订单（入）")
    List<Map<String,Object>> getFacOrderEd2(int fac_id){
        return facOrderServer.getFacOrderEd2(fac_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderWait2",method = RequestMethod.POST)
    @ApiOperation("获得待接收订单（入）")
    List<Map<String, Object>> getFacOrderWait2(int fac_id){
        return facOrderServer.getFacOrderWait2(fac_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderWait1",method = RequestMethod.POST)
    @ApiOperation("获得待接收订单（出）")
    List<Map<String, Object>> getFacOrderWait1(int fac_id){
        return facOrderServer.getFacOrderWait1(fac_id);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderIng1",method = RequestMethod.POST)
    @ApiOperation("获得进行中订单（出）")
    List<Map<String, Object>> getFacOrderIng1(int fac_id){
        return facOrderServer.getFacOrderIng1(fac_id);
    }


    @ResponseBody
    @RequestMapping(path = "/getFacOrderIng2",method = RequestMethod.POST)
    @ApiOperation("获得进行中订单（入）")
    List<Map<String, Object>> getFacOrderIng2(int fac_id){
        return facOrderServer.getFacOrderIng2(fac_id);
    }

    @ResponseBody
    @RequestMapping(path = "/addNewFacOrder",method = RequestMethod.POST)
    @ApiOperation("添加新的工厂订单")
    int addNewFacOrder(@RequestParam int ff_id,@RequestParam int ft_id,@RequestParam int carClass,
                       @RequestParam String goodClass,@RequestParam int targetCarNum,
                       @RequestParam int targetGoodNum,@RequestParam int targetTransportTime,@RequestParam float goodPrice){
        //新建一个工厂订单实体
        FactoryOrder factoryOrder = new FactoryOrder();
        //赋值
        factoryOrder.setFf_id(ff_id);
        factoryOrder.setFt_id(ft_id);
        factoryOrder.setOrder_carclass(carClass);
        factoryOrder.setOrder_goodclass(goodClass);
        factoryOrder.setOrder_actualcarnum(targetCarNum);
        factoryOrder.setOrder_targetcarnum(targetGoodNum);
        factoryOrder.setOrder_transporttime(targetTransportTime);
        factoryOrder.setOrder_goodprice(goodPrice);
        //进行状态赋值
        factoryOrder.setOrder_state(2);
        //开始时间赋值
        factoryOrder.setOrder_startdate(TimeUtils.getNowDate());
        //进行添加
        return facOrderServer.addNewFacOrder(factoryOrder);

    }

    @ResponseBody
    @RequestMapping(path = "/facOrderJieshou",method = RequestMethod.POST)
    @ApiOperation("买家工厂接收新的订单")
    int facOrderJieshou(@RequestParam int fac_orderID){
        return facOrderServer.facOrderJieshou(fac_orderID);
    }

    @ResponseBody
    @RequestMapping(path = "/facOrderJujue",method = RequestMethod.POST)
    @ApiOperation("买家工厂拒绝新的订单，并给出拒绝理由")
    int facOrderJujue(int fac_orderID,String order_refuseReason){
        return facOrderServer.facOrderJujue(fac_orderID,order_refuseReason);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderSonOrder",method = RequestMethod.POST)
    @ApiOperation("获得一个公司订单下的所有子订单")
    List<Map<String ,Object>> getFacOrderSonOrder(int fac_orderId) throws JSONException{
        return facOrderServer.getFacOrderSonOrder(fac_orderId);
    }

    @ResponseBody
    @RequestMapping(path = "/getFacOrderSonOrderEd",method = RequestMethod.POST)
    @ApiOperation("获得一个公司订单下的所有子订单Ed")
    List<Map<String,Object>> getFacOrderSonOrderEd(int fac_orderId){
        return facOrderServer.getFacOrderSonOrderEd(fac_orderId);
    }

}
