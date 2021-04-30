package com.example.coal.bean;

import lombok.Data;

import java.util.Date;

@Data
public class FactoryMessage {

    private int id;
//    消息发送发
    private int from_id;
//    消息接收方
    private int to_id;
//    当前消息状态
    private int messageState;
//    发出时间
    private Date messageDate;
//    内容
    private String from_name;
//    是否为订单发起消息（1：订单发起通知；2：订单回复通知）
    private String content;

}
