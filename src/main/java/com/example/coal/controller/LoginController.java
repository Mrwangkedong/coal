package com.example.coal.controller;

import com.example.coal.server.LoginServe;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class LoginController {
//    LoginServe loginServe = new LoginServe();
    @Autowired
    private LoginServe loginServe;

    @ResponseBody
    @ApiOperation("工厂员工登录")
    @PostMapping(path = "/FacLogin")
    public Map<String ,Object> FacLogin(String phoneNum, String password){
        return loginServe.FacLogin(phoneNum, password);
    }

    @ResponseBody
    @ApiOperation("司机手机端登录")
    @PostMapping(path = "/DriverLogin")
    public Map<String ,Object> DriverLogin(String phoneNum, String password){
        return loginServe.DriverLogin(phoneNum, password);
    }


}
