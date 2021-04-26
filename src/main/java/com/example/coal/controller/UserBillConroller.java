package com.example.coal.controller;


import com.example.coal.bean.UserBill;
import com.example.coal.server.UserBillServer;
import com.example.coal.server.UserWalletServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserBillConroller {
    UserBillServer userBillServer = new UserBillServer();

    @ApiOperation("得到某用户的历史账单")
    @PostMapping("/getUserBillList")
    @ResponseBody
    public List<UserBill> getUserBillList(@RequestParam int user1_id, @RequestParam int bill_state){
        return userBillServer.getUserBillList(user1_id,bill_state);
    }

    @ApiOperation("增加账户账单")
    @PostMapping("/addUserBill")
    @ResponseBody
    public int addUserBill(int user1_id,String user2_name,float bill_money,int bill_type,int bill_state){
        UserBill userBill = new UserBill();
        userBill.setUser1_id(user1_id);
        userBill.setUser2_name(user2_name);
        userBill.setBill_money(bill_money);
        userBill.setBill_type(bill_type);
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        Timestamp timeStamp = new Timestamp(date.getTime());     //   讲日期时间转换为数据库中的timestamp类型
        userBill.setBill_data(timeStamp);
        userBill.setBill_state(bill_state);

        //更新钱包信息
        if (bill_type == 0){
            bill_money = 0 - bill_money;
        }
        int i = new UserWalletServer().editWalletMoney(user1_id, bill_state, bill_money);
        int i1 = userBillServer.addUserBill(userBill);
        if (i == 1 && i1 == 1){
            return 1;
        }else {
            return 0;
        }
    }


}
