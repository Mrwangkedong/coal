package com.example.coal.bean;


import lombok.Data;

@Data
public class FacOrderTestOne{
    private int id;
//    对应化验单id
    private int factory_testid;
//    第几次
    private int no;
//    化验数据
    private float one_mt;
    private float one_ad;
    private float one_std;
    private float one_vdaf;
    private int one_CRC;
    private int one_G;
//    备注
    private String one_note;

}
