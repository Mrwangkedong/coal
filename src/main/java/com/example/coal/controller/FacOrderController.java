package com.example.coal.controller;


import com.example.coal.server.FacOrderServer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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



}
