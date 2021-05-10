package com.example.coal.server;

import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.DriverMsg;
import com.example.coal.dao.DriverMsgMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class DriverMsgServer {
    //        获得sqlsession对象
    public static  SqlSession sqlsession = MybatisUtils.getSqlsession();
    private DriverMsgMapper mapper = sqlsession.getMapper(DriverMsgMapper.class);

//  判断某个司机id是否存在
    public boolean ifExitDriId(int d_id){

        List<Integer> driverIds = mapper.getDriverIds();
        for (Integer driverId : driverIds) {
            if (d_id == driverId){
                return true;
            }
        }
        return false;
    }

//   判断对应司机的id是否对应密码
    public boolean ifPwbTrue(int d_id,String password){

        String driverPwb = mapper.getDriverPwb(d_id);
        return driverPwb.equals(password);
    }


//    根据司机id获得司机的全部信息
    public DriverMsg getDriverMsg(int d_id){

        return mapper.getDriverMsg(d_id);
    }

//    添加新的司机信息
    public int addNewDriver(Map<String ,Object> map){

        int i = mapper.addNewDriver(map);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

//    更改司机信息
    public int editDriverMsg(DriverMsg driverMsg){
        int i = mapper.editDriverMsg(driverMsg);
        sqlsession.commit();
        return i;
    }







}
