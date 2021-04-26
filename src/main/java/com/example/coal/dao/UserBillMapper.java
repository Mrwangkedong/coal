package com.example.coal.dao;

import com.example.coal.bean.UserBill;

import java.util.List;

public interface UserBillMapper {

    //新增指定用户的账单信息
    List<UserBill> getUserBillList(int user1_id,int bill_state);

    //增加用户账单信息
    int addUserBill(UserBill userBill);


}
