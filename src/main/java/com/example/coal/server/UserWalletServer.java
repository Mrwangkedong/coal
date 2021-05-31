package com.example.coal.server;

import com.example.coal.Utils.MybatisUtils;
import com.example.coal.Utils.TimeUtils;
import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.UserBill;
import com.example.coal.bean.UserWallet;
import com.example.coal.dao.DriverMsgMapper;
import com.example.coal.dao.UserBillMapper;
import com.example.coal.dao.UserWalletMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.coal.server.DriverMsgServer.sqlsession;

@Service
public class UserWalletServer{


    UserWalletMapper mapper = sqlsession.getMapper(UserWalletMapper.class);

    //    获取当前用户的wallet信息
    public UserWallet getUserWalletInfo(int user_id, int user_state) {
        return mapper.getWalletInfo(user_id, user_state);
    }

    /**
     * 获得当前用户的账户情况
     * @param user_id 用户id
     * @param user_state 用户身份
     * @return Map<String,Object> {ifBcard,moneyNow}
     */
    public Map<String,Object> getUserWalletInfo2(int user_id, int user_state) {

        Map<String,Object> map = new HashMap<>();
        UserWallet walletInfo = mapper.getWalletInfo(user_id, user_state);
        if (walletInfo == null){
            map.put("ifBcard",0);
        }else {
            map.put("ifBcard",1);
            map.put("moneyNow",walletInfo.getWallet_money());
        }

        return map;
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

    /**
     * 用户绑定/修改银行卡
     * @param user_id 用户id
     * @param wallet_state 用户类别
     * @param userName 持卡人姓名
     * @param BcardNum 银行卡号
     * @return 1/0
     */
    public int addUserWallet(int user_id,int wallet_state,String userName,String BcardNum){
        //先进行查询是否已经存在，如果已经存在，就进行更改
        UserWallet userWalletInfo = getUserWalletInfo(user_id, wallet_state);
        if (userWalletInfo == null){
            int i = mapper.addNewWallet(user_id, wallet_state, userName, BcardNum);
            if (i == 1)
                sqlsession.commit();
            return i;
        }else {
            userWalletInfo.setBcard_name(userName);
            userWalletInfo.setBcard_number(BcardNum);
            int i = mapper.editWalletBcardInfo(userWalletInfo);
            if (i == 1)
                sqlsession.commit();
            return i;
        }
    }

    /**
     * 增加用户余额
     * @param user_id 用户id
     * @param wallet_state 用户身份 司机1 工厂2
     * @param moneyNum 钱数
     * @param billState 账单类型（1：订单金额；2：充值金额）
     * @return 1/0  2-->未绑定银行卡
     */
    public int addWalletMoney(int user_id,int wallet_state,float moneyNum,int billState){
        //获得用户钱包信息
        UserWallet userWalletInfo = getUserWalletInfo(user_id, wallet_state);
        if (userWalletInfo == null){
            return 2;
        }else {
            float moneyNow = userWalletInfo.getWallet_money();
            userWalletInfo.setWallet_money(moneyNow+moneyNum);
            int i = mapper.editWalletBcardInfo(userWalletInfo);
            int i1 = 1;
            //如果是订单返现，则改变管理员余额，若是充值，不改变
            if (billState == 1 || billState == 3){
                i1 = reduceManageMoney(moneyNum,billState,wallet_state,user_id);
            }

            /*
            账单增加
             */
            UserBill userBill = new UserBill();
            userBill.setUser1_id(user_id);
            if (billState == 1)
                userBill.setUser2_name("订单返现");
            else if(billState == 3)
                userBill.setUser2_name("订单保证金退还");
            else userBill.setUser2_name("充值");
            userBill.setBill_money(moneyNum);
            userBill.setBill_state(wallet_state);   //1表示司机  2表示工厂
            userBill.setBill_type(1);
            userBill.setBill_data(TimeUtils.getNowDate());
            UserBillMapper userBillMapperapper = sqlsession.getMapper(UserBillMapper.class);
            int i2 = userBillMapperapper.addUserBill(userBill);

            if (i==1 && i1==1 && i2==1) {
                sqlsession.commit();
                return 1;
            }
            else
                return 0;
        }
    }

    /**
     * 减少用户余额
     * @param user_id 用户id
     * @param wallet_state 用户身份 1为司机 2为工厂
     * @param moneyNum 钱数
     * @param billState 发起订单/订单退费 1 ------- 充值/提现 2
     * @return 1/0  2-->未绑定银行卡 3-->余额不足
     */
    public int reduceWalletMoney(int user_id,int wallet_state,float moneyNum,int billState){
        //获得用户钱包信息
        UserWallet userWalletInfo = getUserWalletInfo(user_id, wallet_state);
        if (userWalletInfo == null){
            return 2;
        }else {
            float moneyNow = userWalletInfo.getWallet_money();
            //如果当前余额小于要减去的金额，返回3.表示余额不足
            if (moneyNum > moneyNow){
                return 3;
            }
            userWalletInfo.setWallet_money(moneyNow-moneyNum);
            int i = mapper.editWalletBcardInfo(userWalletInfo);  //当前用户修改状况
            int i1 = 1;
            if (wallet_state != 3){
//                wallet_state为2时候工厂订单运费  1时为司机订单押金
                i1 = addManageMoney(moneyNum,wallet_state,user_id);  //管理员钱包修改状况
            }

            //如果是司机订单保证金的话，不进行账单生成
            if (wallet_state == 1 && billState == 1 && i==1 && i1==1){
                sqlsession.commit();
                return 1;
            }else if (i1==0 || i==0){
                return 0;
            }

            /*
            增加账单
             */
            UserBill userBill = new UserBill();
            userBill.setUser1_id(user_id);
            if (billState == 1)
                userBill.setUser2_name("发起新订单");
            else userBill.setUser2_name("提现");
            userBill.setBill_money(moneyNum);
            userBill.setBill_state(wallet_state);
            userBill.setBill_type(0);
            userBill.setBill_data(TimeUtils.getNowDate());
            UserBillMapper userBillMapperapper = sqlsession.getMapper(UserBillMapper.class);
            int i2 = userBillMapperapper.addUserBill(userBill);
            if (i==1 && i1==1 && i2==1) {
                sqlsession.commit();
                return 1;
            }
            else
                return 0;
        }
    }

    /**
     * 增加管理员的金钱数目
     * @param moneyNum 金额
     * @return 1/0
     */
    public int addManageMoney(float moneyNum,int wallet_state,int user_id){
        //获得用户钱包信息
        UserWallet userWalletInfo = getUserWalletInfo(999, 3);
        float moneyNow = userWalletInfo.getWallet_money();
        userWalletInfo.setWallet_money(moneyNow+moneyNum);
        int i = mapper.editWalletBcardInfo(userWalletInfo);
        /*
        增加管理员账单
         */
        UserBill userBill = new UserBill();
        userBill.setUser1_id(999);
        if (wallet_state == 2)
            userBill.setUser2_name("工厂订单押金&&工厂id【"+user_id+"】");
        else userBill.setUser2_name("司机订单押金&&司机id【"+user_id+"】");
        userBill.setBill_money(moneyNum);
        userBill.setBill_state(3);
        userBill.setBill_type(1);
        userBill.setBill_data(TimeUtils.getNowDate());
        UserBillMapper userBillMapperapper = sqlsession.getMapper(UserBillMapper.class);
        int i2 = userBillMapperapper.addUserBill(userBill);
        if (i==1 && i2 == 1){
            return 1;   //此处不commit，后续进行
        }else {
            return 0;
        }


    }

    /**
     * 减少管理员的金钱数目
     * @param moneyNum 金额
     * @return 1/0
     */
    public int reduceManageMoney(float moneyNum,int billType,int wallet_state,int user_id){
        //获得用户钱包信息
        UserWallet userWalletInfo = getUserWalletInfo(999, 3);
        float moneyNow = userWalletInfo.getWallet_money();
        userWalletInfo.setWallet_money(moneyNow-moneyNum);
        int i = mapper.editWalletBcardInfo(userWalletInfo);
        /*
        增加管理员账单
         */
        UserBill userBill = new UserBill();
        userBill.setUser1_id(999);
        if (billType == 3 && wallet_state == 2)   //工厂订单
            userBill.setUser2_name("工厂订单运费退还&&工厂id【"+user_id+"】");
        else if (billType == 3 && wallet_state == 1){
            userBill.setUser2_name("司机订单保证金退还&&司机id【"+user_id+"】");
        }else if (billType == 1 && wallet_state == 1){
            userBill.setUser2_name("司机订单运费结算&&司机id【"+user_id+"】");
        }

        userBill.setBill_money(moneyNum);
        userBill.setBill_state(3);
        userBill.setBill_type(0);
        userBill.setBill_data(TimeUtils.getNowDate());
        UserBillMapper userBillMapperapper = sqlsession.getMapper(UserBillMapper.class);
        int i2 = userBillMapperapper.addUserBill(userBill);
        if (i==1 && i2 == 1){
//            sqlsession.commit();
            return 1;  //此处不commit，后续进行
        }else {
            return 0;
        }

    }



}
