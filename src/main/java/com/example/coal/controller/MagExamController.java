package com.example.coal.controller;

import com.example.coal.server.MagExamServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
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

    MagExamServer magExamServer = new MagExamServer();

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



}
