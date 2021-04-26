package com.example.coal.server;

import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.UserWallet;
import com.example.coal.dao.DriverMsgMapper;
import com.example.coal.dao.UserWalletMapper;
import org.apache.ibatis.session.SqlSession;
import static com.example.coal.server.DriverMsgServer.sqlsession;

public class UserWalletServer{

    UserWalletMapper mapper = sqlsession.getMapper(UserWalletMapper.class);

    //    获取当前用户的wallet信息
    public UserWallet getUserWalletInfo(int user_id, int user_state) {
        return mapper.getWalletInfo(user_id, user_state);
    }

    //    绑定(更换)银行卡操作
    public int editWalletBcardInfo(UserWallet userWallet) {
//        进行DriverMsg操作
        SqlSession sqlsession2 = MybatisUtils.getSqlsession();
        DriverMsgMapper msgMapper = sqlsession2.getMapper(DriverMsgMapper.class);

//        更新wallet表中的数据(司机)
        int i1 = mapper.editWalletInfoDri(userWallet);
//        更改driverMsg中的数据
//        获得对应的drivermsg
        DriverMsg driverMsg = msgMapper.getDriverMsg(userWallet.getUser_id());
        driverMsg.setD_ifbcard(1);
        int i2 = msgMapper.editDriverMsg(driverMsg);

        if (i1 != 0 && i2 != 0) {
            sqlsession2.commit();
            sqlsession.commit();
            return 1;
        } else {
            return 0;
        }
    }

//    更改钱包money
    public int editWalletMoney(int user_id,int wallet_state,float moneyNum){
//        根据user_id，获得记录
        UserWallet userWalletInfo = getUserWalletInfo(user_id,wallet_state);
        float moneyNow = userWalletInfo.getWallet_money();
        userWalletInfo.setWallet_money(moneyNow+moneyNum);
        int i = editWalletBcardInfo(userWalletInfo);
        if (i==1) {
            sqlsession.commit();
            return 1;
        }
        else
            return 0;
    }

}
