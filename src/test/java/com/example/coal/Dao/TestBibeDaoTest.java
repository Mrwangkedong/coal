package com.example.coal.Dao;


import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.*;
import com.example.coal.dao.DriverMsgMapper;
import com.example.coal.dao.DriverOrderMapper;
import com.example.coal.dao.FacOrderMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;


public class TestBibeDaoTest {




    @Test
    void test(){
        SqlSession sqlsession = MybatisUtils.getSqlsession();
        DriverOrderMapper mapper = sqlsession.getMapper(DriverOrderMapper.class);
    }

    @Test
    void test2(){
        SqlSession sqlsession = MybatisUtils.getSqlsession();
        DriverMsgMapper mapper = sqlsession.getMapper(DriverMsgMapper.class);
        DriverMsg driverMsg = mapper.getDriverMsg(1);
        driverMsg.setId(88);
//        d_password,d_name,d_phonenum,d_sex
        System.out.println(mapper.editDriverMsg(driverMsg));

    }

    @Test
    void test3(){
        SqlSession sqlsession = MybatisUtils.getSqlsession();
        FacOrderMapper mapper = sqlsession.getMapper(FacOrderMapper.class);
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(6);



//        更新操作
        facOrderInfo.setOrder_state(1);
        mapper.editFacOrder(facOrderInfo);
        sqlsession.commit();
    }

}
