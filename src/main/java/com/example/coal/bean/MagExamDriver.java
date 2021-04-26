package com.example.coal.bean;

import lombok.Data;

import java.util.Date;

/***
 * 司机资质审核
 */
@Data
public class MagExamDriver {
    private int id;
//    对应司机id
    private int driver_id;
    //    身份证号码
    private String pc_number;
    //    身份证照片地址（正）
    private String pc_address1;
    //    身份证照片地址（反）
    private String pc_address2;
    //    司机出生日期
    private Date d_birthdate;
    //    司机第一次领证日期
    private Date dcard_firstlicencedate;
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
