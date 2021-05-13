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
    /**
     * 获得所有司机id
     * @return  List
     */
    List<Integer> getDriverIds();


    /**
     * 根据d_id查找司机的密码
     * @param d_id 司机id
     * @return 司机密码
     */
    String getDriverPwb(int d_id);

    /**
     * 根据d_id查找司机的信息
     * @param d_id
     * @return
     */
    DriverMsg getDriverMsg(int d_id);

    /**
     * 根据d_id查找司机的钱包信息
     * @param d_id
     * @return
     */
    UserWallet getWalletMsg(int d_id);

//    新增司机
    int addNewDriver(DriverMsg dirverMsg);

//    （一下整合成，修改司机个人信息）
    int editDriverMsg(DriverMsg dirverMsg);

    /**
     * 根据手机号获得用户详情
     * @param phoneNum
     * @return
     */
    DriverMsg getDriverMsg2(String phoneNum);

    /**
     * 获得所有的司机用户信息
     * @return
     */
    List<DriverMsg> getDriMsgs();
}
