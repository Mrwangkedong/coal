package com.example.coal.controller;

import com.example.coal.server.UserWalletServer;
import com.example.coal.bean.UserWallet;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserWalletController {

    UserWalletServer userWalletServer = new UserWalletServer();

    @ResponseBody
    @ApiOperation("获取当前用户的wallet信息")
    @RequestMapping(path = "/getUserWalletInfo",method = RequestMethod.POST)
    public UserWallet getUserWalletInfo(@RequestParam int user_id, @RequestParam int user_state){
        return userWalletServer.getUserWalletInfo(user_id,user_state);
    }

    @ResponseBody
    @ApiOperation("更改司机银行卡信息")
    @RequestMapping(path = "/editWalletBcardInfo",method = RequestMethod.POST)
    public int editWalletBcardInfo(@RequestParam String bacrd_number,
                                   @RequestParam String bcard_name,@RequestParam String bcard_pcarNum,
                                   @RequestParam int user_id,@RequestParam int wallet_state){
        //对传过来的信息进行验证，是否符合规范
        if (bacrd_number.length() != 19 || bcard_pcarNum.length() != 18){
            //银行卡/身份证格式不符合规范
            return 2;
        }
        //进行绑定
        UserWallet userWalletInfo = userWalletServer.getUserWalletInfo(user_id, wallet_state);
        userWalletInfo.setBcard_name(bcard_name);
        userWalletInfo.setBcard_pcardnum(bcard_pcarNum);
        userWalletInfo.setBcard_number(bacrd_number);
        userWalletInfo.setWallet_state(wallet_state);
        return userWalletServer.editWalletBcardInfo(userWalletInfo);
    }

    @ResponseBody
    @ApiOperation("更改司机银行卡金额")
    @RequestMapping(path = "/editWallet",method = RequestMethod.POST)
    public int editWallet(@RequestParam int user_id,@RequestParam int wallet_state,
                          @RequestParam float editMoney,@RequestParam int operator){
        if (operator == 0)
            editMoney = 0 - editMoney;
        return userWalletServer.editWalletMoney(user_id,wallet_state,editMoney);
    }




















}
