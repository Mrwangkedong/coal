package com.example.coal.bean;


import com.example.coal.dao.DriverMsgMapper;
import lombok.Data;

import java.util.Date;
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
//    里程数
    private float d_distance;
    //cid(手机标识码)
    private String d_cid;
    //    信息是否合格
    private int d_ifqualified;
//    是否进行银行卡绑定
    private int d_ifbcard;
    //    身份证号码
    private String pcard_number;
    //    身份证照片地址（正）
    private String pcard_photoaddress1;
    //    身份证照片地址（反）
    private String pcard_photoaddress2;
    //    司机出生日期
    private Date d_birthdate;
    //    司机车辆类型
    private int dcard_carclass;
    //    此次驾驶证开始时间
    private Date dcard_validfrom;
    //    驾驶证有效日期
    private int dcard_validfor;
    //    驾驶证照片地址
    private String dcard_photo;
    //    车辆照片地址
    private String dcard_carphoto;
    //    车牌号码
    private String dcard_carnumber;
}
