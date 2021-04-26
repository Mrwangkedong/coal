package com.example.coal.server;

import com.example.coal.bean.UserBill;
import com.example.coal.dao.UserBillMapper;

import java.util.List;
import static com.example.coal.server.DriverMsgServer.sqlsession;

public class UserBillServer {

    UserBillMapper mapper = sqlsession.getMapper(UserBillMapper.class);

    public List<UserBill> getUserBillList(int user1_id, int bill_state){
        return mapper.getUserBillList(user1_id,bill_state);
    }

    /***
     * 增加用户账单信息
     * @param userBill 用户账单信息实体
     * @return 1/0
     */
    public int addUserBill(UserBill userBill){
        int i = mapper.addUserBill(userBill);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

}
