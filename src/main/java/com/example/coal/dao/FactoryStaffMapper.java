package com.example.coal.dao;

import com.example.coal.bean.FactoryStaff;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface FactoryStaffMapper {

    /***
     * 获得工厂中的全部员工
     * @param fac_id 工厂id
     * @return List
     */
    List<FactoryStaff> getAllStall(int fac_id);

    /**
     * 添加新的工厂员工
     * @param factoryStaff 实体
     * @return 1/0
     */
    int addNewStaff(FactoryStaff factoryStaff);

    /***
     * 获得员工的实体信息
     * @param staff_id 员工id
     * @return 员工信息实体
     */
    FactoryStaff getStaffInfo(int staff_id);

    /**
     * 在审核申请新工厂时，返回新工厂管理员信息
     * @param fac_id 工厂id
     * @return 管理员信息
     */
    FactoryStaff getFacManager(int fac_id);


    /***
     * 更改员工的基本信息
     * @param factoryStaff 员工实体信息
     * @return 1/0
     */
    int editStaffInfo(FactoryStaff factoryStaff);

    /**
     * 删除某个员工信息
     * @param staff_id 员工id
     * @return 1/0
     */
    int deleteStaffInfo(int staff_id);

    /**
     * 通过手机号获得员工密码
     * @param phoneNum
     * @return
     */
    FactoryStaff getStaffInfoByPhone(String phoneNum);

    /**
     * 通过手机号获得员员工信息列表【查看是否被多次注册】
     * @param phoneNum 手机号码
     * @return 注册手机号几个
     */
    int getStaffsByPhone(String phoneNum);




    /**
     * 删除工厂名下所有职员
     * @param fac_id 工厂id
     * @return 1/0
     */
    int deleteFacStaff(int fac_id);

    /**
     * 获得全部工厂员工
     * @return
     */
    List<FactoryStaff> getAllStaff();
}
