package com.example.coal.controller;


import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.UserWallet;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.coal.server.DriverMsgServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class DriverMsgController{

    DriverMsgServer driverMsgServer = new DriverMsgServer();

    @ResponseBody
    @ApiOperation("查看id是否存在")
    @RequestMapping(path = "/ifExitDriId",method = RequestMethod.POST)
    public boolean ifExitDriId(@ApiParam("输入一个id")@RequestParam int d_id){
        return driverMsgServer.ifExitDriId(d_id);
    }

    @ResponseBody
    @ApiOperation("判断账号密码是否匹配")
    @RequestMapping(path = "/ifPwbTrue",method = RequestMethod.POST)
    public boolean ifPwbTrue(@ApiParam("输入一个id")@RequestParam int d_id,@ApiParam("输入密码")@RequestParam String password){
        return driverMsgServer.ifPwbTrue(d_id,password);
    }

    @ResponseBody
    @ApiOperation("根据id获得司机的个人信息")
    @RequestMapping(path = "/getDriverMsg",method = RequestMethod.POST)
    public DriverMsg getDriverMsg(@ApiParam("输入一个id")@RequestParam int d_id){
        return driverMsgServer.getDriverMsg(d_id);
    }

    @ResponseBody
    @ApiOperation("更改司机的登录密码")
    @RequestMapping(path = "/editDriverPwb",method = RequestMethod.POST)
    public int editDriverPwb(@ApiParam("司机d_id")@RequestParam int d_id,@ApiParam("司机新密码")@RequestParam String newPassword){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(d_id);
        driverMsg.setD_password(newPassword);
        return driverMsgServer.editDriverMsg(driverMsg);
    }

    @ResponseBody
    @ApiOperation("更改司机的登录cid")
    @RequestMapping(path = "/editDriverCid",method = RequestMethod.POST)
    public int editDriverCid(@ApiParam("司机d_id")@RequestParam int driver_id,@ApiParam("司机新cid")@RequestParam String newCid){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(driver_id);
        driverMsg.setD_cid(newCid);
        return driverMsgServer.editDriverMsg(driverMsg);
    }

    @ResponseBody
    @ApiOperation("更改司机的支付密码")
    @RequestMapping(path = "/editDriverPayPwb",method = RequestMethod.POST)
    public int editDriverPayPwb(@ApiParam("司机d_id")@RequestParam int d_id,@ApiParam("司机新支付密码")@RequestParam String newPayPwb){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(d_id);
        driverMsg.setD_pay_password(newPayPwb);
        return driverMsgServer.editDriverMsg(driverMsg);
    }

    @ResponseBody
    @ApiOperation("更改司机的手机号")
    @RequestMapping(path = "/editDriverPhoneNum",method = RequestMethod.POST)
    public int editDriverPhoneNum(@ApiParam("司机d_id")@RequestParam int d_id,@ApiParam("司机新密码")@RequestParam String newPhoneNum){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(d_id);
        driverMsg.setD_phonenum(newPhoneNum);
        return driverMsgServer.editDriverMsg(driverMsg);
    }


    @ResponseBody
    @ApiOperation("更改司机的银行卡绑定情况")
    @RequestMapping(path = "/editDriverBcard",method = RequestMethod.POST)
    public int editDriverBcard(@ApiParam("司机d_id")@RequestParam int d_id,@ApiParam("绑定银行卡")@RequestParam int d_ifbcard){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(d_id);
        driverMsg.setD_ifbcard(d_ifbcard);
        return driverMsgServer.editDriverMsg(driverMsg);
    }


    @ResponseBody
    @ApiOperation("更改司机的资质绑定情况")
    @RequestMapping(path = "/editDriverQualified",method = RequestMethod.POST)
    public int editDriverQualified(@ApiParam("司机d_id")@RequestParam int d_id,@ApiParam("司机资质情况")@RequestParam int d_ifqualified){
        DriverMsg driverMsg = driverMsgServer.getDriverMsg(d_id);
        driverMsg.setD_ifqualified(d_ifqualified);
        return driverMsgServer.editDriverMsg(driverMsg);
    }

    @ResponseBody
    @ApiOperation("获得所有司机信息")
    @RequestMapping(path = "/getAllDriMsg",method = RequestMethod.GET)
    public List<DriverMsg> getAllDriMsg(){
        return driverMsgServer.getAllDriMsg();
    }

    @ResponseBody
    @ApiOperation("添加新司机")
    @RequestMapping(path = "/addNewDriver",method = RequestMethod.POST)
    public int addNewDriver(DriverMsg driverMsg){
        System.out.println(driverMsg);
        return 1;
    }

}
