package com.example.coal.Utils;

import java.sql.Timestamp;

public class TimeUtils {

    public static Timestamp getNowDate(){
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        return new Timestamp(date.getTime());
    }
}
