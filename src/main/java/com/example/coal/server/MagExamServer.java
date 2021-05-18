package com.example.coal.server;

import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryQualified;
import com.example.coal.dao.DriverMsgMapper;
import com.example.coal.dao.FacMsgMapper;
import com.example.coal.dao.FactoryStaffMapper;
import com.example.coal.dao.MagExamMapper;

import com.example.coal.Utils.FileMoveUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.coal.server.DriverMsgServer.sqlsession;

@Service
public class MagExamServer {

    MagExamMapper magExamMapper = sqlsession.getMapper(MagExamMapper.class);
    FacMsgMapper facMsgMapper = sqlsession.getMapper(FacMsgMapper.class);
    FactoryStaffMapper factoryStaffMapper = sqlsession.getMapper(FactoryStaffMapper.class);

    DriverMsgMapper driverMsgMapper = sqlsession.getMapper(DriverMsgMapper.class);
    /**
     * 获得所有新申请订单的列表
     * @return List
     */
    public List<Map<String,Object>> getNewFacList(){
        List<Map<String,Object>> newFacList = new ArrayList<>();
        //获得全部信息
        List<FactoryMsg> newFacInfo = magExamMapper.getNewFacInfo();
        //通过for循环检索有用的信息
        for (FactoryMsg factoryMsg : newFacInfo) {
            Map<String,Object> map = new HashMap<>();
            map.put("fac_id",factoryMsg.getId());
            map.put("fac_name",factoryMsg.getName());
            map.put("fac_lpname",factoryMsg.getFactory_lpname());
            map.put("fac_address",factoryMsg.getFactory_address());
            newFacList.add(map);
        }
        return newFacList;
    }

    /**
     * 通过新工厂申请
     * @param fac_id 工厂id
     * @return 1/0
     */
    public int passNewFacInfo(int fac_id){
        /*
        此处应该有短信/电话通知
         */
        return new FacMsgServer().editFacIfPass(fac_id, 1);
    }

    /**
     * 拒绝新工厂申请
     * @param fac_id 工厂id
     * @return 1/0
     */
    public int refuseNewFacInfo(int fac_id){
        /*
        此处应该有短信/电话通知
         */
        /*
        删除该工厂已注册managerStaff
         */
        int i = factoryStaffMapper.deleteFacStaff(fac_id);
        if (i == 0){
            return i;
        }
        return new FacMsgServer().editFacIfPass(fac_id, 0);
    }


    /**
     * 获得所有申请修改工厂信息的工厂列表
     * @return List
     */
    public List<Map<String,Object>> getEditFacList(){
        List<Map<String,Object>> newFacList = new ArrayList<>();
        //获得全部信息
        List<FactoryQualified> newFacInfo = magExamMapper.getEditFacInfo();
        //通过for循环检索有用的信息
        for (FactoryQualified factoryMsg : newFacInfo) {
            Map<String,Object> map = new HashMap<>();
            map.put("fac_id",factoryMsg.getFactory_id());
            map.put("fac_name",factoryMsg.getName());
            map.put("fac_lpname",factoryMsg.getFactory_lpname());
            map.put("fac_address",factoryMsg.getFactory_address());
            newFacList.add(map);
        }
        return newFacList;
    }


    /**
     * 通过工厂信息修改申请
     * @param fac_id 工厂id
     * @return 1/0  -1表示文件转移出错
     */
    public int passEditFacInfo(int fac_id){
        FactoryQualified FacQualified = magExamMapper.getEditFacQualifiedByFacId(fac_id);
        /*
        进行图片转移
         */
        int i = FileMoveUtils.facPhotoMove(fac_id);
        if (i==-1)
            return i;
        /*
        进行消息通知
         */
        int i1 = new FacMessageServer().addNewMessageFromMag(fac_id, "修改申请通过");
        if (i1 == 0){
            return i;
        }
        /*
        进行信息更新
         */
        FactoryMsg facInfo = new FacMsgServer().getFacInfo(fac_id);
        facInfo.setName(FacQualified.getName());
        facInfo.setFactory_ifpass(1);
        facInfo.setFactory_address(FacQualified.getFactory_address());
        facInfo.setFactory_longitude(FacQualified.getFactory_longitude());
        facInfo.setFactory_latitude(FacQualified.getFactory_latitude());
        facInfo.setFactory_lpname(FacQualified.getFactory_lpname());
        facInfo.setFactory_lpcardnum(FacQualified.getFactory_lpcardnum());
        return new FacMsgServer().editFacMsg(facInfo);
    }

    /**
     * 拒绝通过厂家修改申请
     * @param fac_id 工厂id
     * @param refuseReason 拒绝原因
     * @return 1/0
     */
    public int refuseEditFacInfo(int fac_id,String refuseReason){
        FactoryMsg facInfo = new FacMsgServer().getFacInfo(fac_id);
        facInfo.setFactory_ifpass(1);
        /*
        进行消息通知
         */
        new FacMessageServer().addNewMessageFromMag(fac_id, "拒绝通过：" + refuseReason);
        int i1 = facMsgMapper.editFacMsg(facInfo);
        if (i1 == 1){
            sqlsession.commit();
        }
        return i1;
    }

//    ***********司机审批操作******************************************************************************************************

    /**
     * 获得全部新司机申请
     * @return List
     */
    public List<DriverMsg> getNewDriverList(){
        return magExamMapper.getNewDriverList();
    }
    /**
     * 获得全部司机修改申请
     * @return List
     */
    public List<DriverMsg> getEdirDriverList(){
        return magExamMapper.getEdirDriverList();
    }

    /**
     * 通过信息机的申请
     * @param d_id 司机id
     * @return 1/0    -1:已操作过
     */
    public int passNewDriver(int d_id){
        DriverMsg driverMsg = driverMsgMapper.getDriverMsg(d_id);
        if (driverMsg.getD_ifqualified() != 2){
            return -1;
        }
        driverMsg.setD_ifqualified(1);
        int i = driverMsgMapper.editDriverMsg(driverMsg);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

    /**
     * 拒绝申请
     * @param d_id 司机id
     * @param refuseReason 拒绝原因
     * @return 1/0        -1:已操作过
     */
    public int refuseNewDriver(int d_id,String refuseReason){
        DriverMsg driverMsg = driverMsgMapper.getDriverMsg(d_id);
        if (driverMsg.getD_ifqualified() != 2){
            return -1;
        }
        driverMsg.setD_ifqualified(0);
        int i = driverMsgMapper.editDriverMsg(driverMsg);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }
}
