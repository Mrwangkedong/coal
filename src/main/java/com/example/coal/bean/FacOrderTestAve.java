package com.example.coal.bean;


import lombok.Data;

/***
 * 化验单平均数据
 */
@Data
public class FacOrderTestAve {
    private int id;
//    对应化验单id
    private int factory_testid;
//    成分数据
    private float ave_mt;
    private float ave_ad;
    private float ave_std;
    private float ave_vdaf;
    private int ave_CRC;
    private int ave_G;
//    备注
    private String ave_note;



}
