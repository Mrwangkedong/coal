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

    /**
     * 通过手机号码获得用户信息
     * @param phoneNum 手机号码
     * @return 司机信息实体
     */
    DriverMsg getDriverMsg2(String phoneNum);



}
