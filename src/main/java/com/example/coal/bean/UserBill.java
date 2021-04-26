package com.example.coal.bean;

import lombok.Data;

import java.util.Date;

@Data
public class UserBill {
    private int id;
//    账单主人当前用户的id
    private int user1_id;
//    账单另一方姓名
    private String user2_name;
//    账单金额
    private float bill_money;
//    账单发生时间
    private Date bill_data;
//    账单类型：1表示收入；0表示支出
    private int bill_type;
    //用户身份
    private int bill_state;

}
