package com.example.coal.dao;

import com.example.coal.bean.UserWallet;

public interface UserWalletMapper {
//    获得钱包信息all（d_id）
     UserWallet getWalletInfo(int user_id,int wallet_state);
//    添加新的钱包信息（添加新用户的时候，直接进行钱包的添加【user_id,wallet_state】）
    int addNewWallet(int user_id,int wallet_state);
//    更改钱包金额信息/银行卡信息（记得同时更新账单信息）
    int editWalletInfoDri(UserWallet userWallet);

}
