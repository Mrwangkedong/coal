package com.example.coal.bean;

import lombok.Data;

@Data
public class FacOrderTest {
    private int id;
//    对应的订单id
    private int factory_orderid;
//    化验员id
    private int test_conductorid;
//    化验次数
    private int test_num;
//    平均数据id
    private int test_avedataid;
}
