package com.example.coal.bean;


import lombok.Data;

@Data
public class FactoryMsg {
    private int id;
//    工厂名称
    private String name;
//    工厂地址
    private String factory_address;
//    资质审核是否通过  1表示通过 ， 0表示通过，2表示新工厂审核中，3表示工厂资质修改审核中
    private int factory_ifpass;
    //经度
    private float factory_longitude;
    //纬度
    private float factory_latitude;
    //法人姓名
    private String factory_lpname;
    //法人身份证号码
    private String factory_lpcardnum;
//    法人身份证照片1
    private String factory_lpcardphoto1;
//    法人身份证照片2
    private String factory_lpcardphoto2;
//    工厂营业资格证照片
    private String factory_licencephoto;

}
