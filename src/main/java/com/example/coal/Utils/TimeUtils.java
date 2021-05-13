package com.example.coal.Utils;


import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    public static Timestamp getNowDate(){
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        return new Timestamp(date.getTime());
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date fromDate2 = simpleFormat.parse("2018-03-01 12:00");
        Date toDate2 = simpleFormat.parse("2018-03-12 12:00");
        System.out.println(ifOntime(toDate2, fromDate2, 23));
    }

    public static int ifOntime(Date toDate2, Date fromDate2, int hoursNum) {
        long timeStart = fromDate2.getTime();
        long time = toDate2.getTime();
        int hours = (int) ((time - timeStart) / (1000 * 60 * 60));
        System.out.println(hours);
        if (hours <= hoursNum)
            return 1;
        else return 0;
    }

    /**
     * 转化字符串日期为Date
     * @param stringDate 字符串日期
     * @return Date
     * @throws ParseException 11
     */
    public static Date dateTransform(String stringDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        return sdf.parse(stringDate);
    }

}
