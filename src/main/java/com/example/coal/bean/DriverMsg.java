package com.example.coal.bean;


import com.example.coal.dao.DriverMsgMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

/***
 * 司机个人信息（factory_msg)
 */
@Data
public class DriverMsg{
    private int id;
    private String d_password;
    private String d_name;
    private String d_phonenum;
    private String d_sex;
//    支付密码
    private String d_pay_password;
//    头像图片存储地址
    private String d_txaddress;
//    里程数
    private float d_distance;
    //cid(手机标识码)
    private String d_cid;

    //    信息是否合格
    private int d_ifqualified;
//    是否进行银行卡绑定
    private int d_ifbcard;
}
