package com.example.coal.Utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {


    public static void dateTest() throws ParseException {
//        String date = "2021-04-16";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2021-04-16";
        java.util.Date date=sdf.parse(dstr);
        System.out.println(date);
    }

    public static void main(String[] args) throws ParseException {
        dateTest();
    }


}
