package com.example.coal.bean;

import lombok.Data;

import java.util.Date;

/***
 * 司机资质信息（driver_qualified）
 */
@Data
public class DriverQualified {
    private int id;
//    对应司机的id
    private int driver_id;
    //司机密码
    private String d_password;
    //司机姓名
    private String d_name;
//    手机号
    private String d_phonenum;
//    性别
    private String d_sex;
    //    支付密码
    private String d_pay_password;
    //    里程数
    private float d_distance;
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
