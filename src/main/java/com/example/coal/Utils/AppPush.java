package com.example.coal.Utils;

import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "c4rR2PZbvyACmenDkiGoZ2";
    private static String appKey = "AQRPS9N34i9XT547soLzr1";
    private static String masterSecret = "fhbSEVuwYW8Bxh73XAbLn7";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static IGtPush push = new IGtPush(url, appKey, masterSecret);

    /***
     * 推送给全体用户
     * @param content 内容
     * @param fac_orderid 工厂id
     */
    public void pushAll(String content,int fac_orderid){
        String msg = "{title:\"新12订单\",content:\""+content+"\",payload:\"{action:'orderWait',fac_orderid:"+fac_orderid+"}\"}";

        TransmissionTemplate t = new TransmissionTemplate();
        t.setAppId(appId);
        t.setAppkey(appKey);
        t.setTransmissionContent(msg);
        t.setTransmissionType(2);

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(t);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }

    public boolean sendMessageAndroid(String cid, String content,int orderID,int fac_orderid) {

        String msg = "{title:\"订单修改\",content:\""+content+"\",payload:\"{action:'orderNow',orderID:"+orderID+",fac_orderid:"+fac_orderid+"}\"}";

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(APPInfo.APPID);
        template.setAppkey(APPInfo.APPKEY);
        template.setTransmissionType(2);
        template.setTransmissionContent(msg);

        SingleMessage message = new SingleMessage();
        message.setOffline(true);

        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(APPInfo.APPID);
        target.setClientId(cid);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null && ret.getResponse() != null && ret.getResponse().containsKey("result")) {
            System.out.println(ret.getResponse().toString());
            return ret.getResponse().get("result").toString().equals("ok") && ret.getResponse().containsKey("status");
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

//        String cid = "2c31442284485cc4a0ced1acbac30556";
//        String title = "测试标题";
//        String content = "测试内容";
//
//        new AppPush().pushOne(cid,title,content);

        new AppPush().pushAll("大秀山煤业发布新的订单",6);
    }
}
