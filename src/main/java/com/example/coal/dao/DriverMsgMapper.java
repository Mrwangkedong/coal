package com.example.coal.dao;


import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.DriverQualified;
import com.example.coal.bean.UserWallet;

import java.util.List;
import java.util.Map;

/****
 * 对司机的个人信息进行操作
 */
public interface DriverMsgMapper {
//    获取或有司机id
    List<Integer> getDriverIds();
//    根据d_id查找司机的密码
    String getDriverPwb(int d_id);
//    根据d_id查找司机的信息
    DriverMsg getDriverMsg(int d_id);
//    根据d_id查找司机的钱包信息
    UserWallet getWalletMsg(int d_id);
//    根据d_id查看司机的运行资格信息
    DriverQualified getDQualified(int d_id);
//    新增司机
    int addNewDriver(Map<String,Object> driverMap);


//    （一下整合成，修改司机个人信息）
    int editDriverMsg(DriverMsg dirverMsg);
//    更改密码
//    更改支付密码
//    修改司机是否完成认证
//    修改司机是否完成银行卡绑定
//    修改司机的里程数
//    修改司机的头像（地址）
//    修改司机姓名


}
