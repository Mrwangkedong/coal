package com.example.coal.bean;

import java.util.Date;

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
    private String content;

}
