package com.example.coal.server;
import com.example.coal.bean.FactoryStaff;
import com.example.coal.dao.FacOrderMapper;
import com.example.coal.dao.FactoryStaffMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.coal.server.DriverMsgServer.sqlsession;

public class FacStaffServer {
    FactoryStaffMapper mapper = sqlsession.getMapper(FactoryStaffMapper.class);

    /***
     * 获得某个工厂全部的员工信息
     * @param fac_id 工厂id
     * @return List
     */
    public List<FactoryStaff> getAllStaff(int fac_id){
        return mapper.getAllStall(fac_id);
    }

    /***
     * 添加新的员工信息
     * @param factoryStaff
     * @return
     */
    public int addNewStaff(FactoryStaff factoryStaff){
        int i = mapper.addNewStaff(factoryStaff);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

    /***
     * 获得员工个人信息
     * @param staff_id 员工id
     * @return 员工信息实体
     */
    public FactoryStaff getStaffInfo(int staff_id){
        return mapper.getStaffInfo(staff_id);
    }


    /***
     * 修改密码
     * @param staff_id 用户信息id
     * @param newPwb 新的密码信息
     * @return 1/0
     */
    public int editStaffPwb(int staff_id,String newPwb){
        //根据id获得实体信息
        FactoryStaff staffInfo = mapper.getStaffInfo(staff_id);
        //设置新的密码
        staffInfo.setStaff_password(newPwb);
        //进行更新
        int i = mapper.editStaffInfo(staffInfo);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

    /***
     * 更新员工的出生日期
     * @param staff_id 员工id
     * @param newBirthDay 新的出生日期
     * @return 1/0
     * @throws ParseException 。。
     */
    public int editStaffBirthday(int staff_id,String newBirthDay) throws ParseException {
        //进行日期格式转化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        java.util.Date newDate=sdf.parse(newBirthDay);
        //根据id获得实体信息
        FactoryStaff staffInfo = mapper.getStaffInfo(staff_id);
        //设置新的日期
        staffInfo.setStaff_birthday(newDate);
        //进行更新
        int i = mapper.editStaffInfo(staffInfo);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

    /***
     * 更新其他属性(手机号、身份证、员工类别，家庭住址）
     * @param factoryStaff 工厂员工实体信息
     * @return 1/0
     */
    public int editStaffInfo(FactoryStaff factoryStaff){
        int i = mapper.editStaffInfo(factoryStaff);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }


    /***
     * 根据id删除某个员工信息
     * @param staff_id 员工id
     * @return 1/0
     */
    public int deleteStaffInfo(int staff_id){
        int i = mapper.deleteStaffInfo(staff_id);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }

}
