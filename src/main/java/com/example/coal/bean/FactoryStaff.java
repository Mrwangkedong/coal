package com.example.coal.bean;


import lombok.Data;

import java.util.Date;

@Data
public class FactoryStaff {

    private int id;
//    对应工厂id
    private int factory_id;
//    手机号码
    private String staff_phonenum;
//    姓名
    private String staff_name;
//    性别
    private String staff_sex;
//    密码
    private String staff_password;
//    员工类别
    private int staff_class;
//    员工出生日期
    private Date staff_birthday;
//    员工身份证号码
    private String staff_pcrdnumber;
//    员工家庭住址
    private String staff_place;



}
