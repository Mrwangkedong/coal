package com.example.coal.bean;


import lombok.Data;

/***
 * 用户钱包信息
 */
@Data
public class UserWallet {
    private int id;
//    对应用户id（司机id、工厂id）
    private int user_id;
//    银行卡持卡人姓名
    private String bcard_name;
//    持卡人身份证号码
    private String bcard_pcardnum;
//    用户银行卡照片
    private String bcard_photo;
//    用户银行卡号码
    private String bcard_number;
//    当前钱包余额
    private float wallet_money;
//    用户身份
    private int wallet_state;

}
