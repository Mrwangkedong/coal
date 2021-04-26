package com.example.coal.bean;

import lombok.Data;

/***
 * 工厂资质审核
 */
@Data
public class MagExamFac {
    private int id;
    //    对应工厂id
    private int factory_id;
    //    工厂营业执照照片
    private int factory_licencephoto;
    //    工厂法人姓名
    private String factory_lpname;
    //    工厂法人身份证照片（正）
    private String factory_lpcardphoto1;
    //    工厂法人身份证照片（反）
    private String factory_lpcardphoto2;
    //    工厂法人身份证号码
    private String factory_lpcardnum;
}
