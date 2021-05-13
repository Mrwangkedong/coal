package com.example.coal.controller;

import com.example.coal.bean.DriverMsg;
import com.example.coal.server.MagExamServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Map;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class MagExamController {

    @Autowired
     private MagExamServer magExamServer ;

    @ResponseBody
    @ApiOperation("获得全部待申请工厂信息列表")
    @GetMapping("/getNewFacList")
    public List<Map<String,Object>> getNewFacList(){
        return magExamServer.getNewFacList();
    }

    @ResponseBody
    @ApiOperation("获得全部待申请修改工厂信息列表")
    @GetMapping("/getEditFacList")
    public List<Map<String,Object>> getEditFacList(){
        return magExamServer.getEditFacList();
    }

    @ResponseBody
    @ApiOperation("通过新工厂申请")
    @PostMapping("/passNewFacInfo")
    public int passNewFacInfo(int fac_id){
        return magExamServer.passNewFacInfo(fac_id);
    }

    @ResponseBody
    @ApiOperation("拒绝新工厂申请")
    @PostMapping("/refuseNewFacInfo")
    int refuseNewFacInfo(int fac_id){
        return magExamServer.refuseNewFacInfo(fac_id);
    }

    @ResponseBody
    @ApiOperation("同意工厂信息修改申请")
    @PostMapping("/passEditFacInfo")
    int passEditFacInfo(int fac_id){
        return magExamServer.passEditFacInfo(fac_id);
    }

    @ResponseBody
    @ApiOperation("拒绝工厂信息修改申请")
    @PostMapping("/refuseEditFacInfo")
    int refuseEditFacInfo(int fac_id,String refuseReason){
        return magExamServer.refuseEditFacInfo(fac_id,refuseReason);
    }

//    ***********司机审批操作******************************************************************************************************
    @ResponseBody
    @ApiOperation("获得全部新司机申请")
    @GetMapping("/getNewDriverList")
    public List<DriverMsg> getNewDriverList(){
        return magExamServer.getNewDriverList();
    }

}
