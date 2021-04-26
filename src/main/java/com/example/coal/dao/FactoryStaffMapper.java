package com.example.coal.dao;

import com.example.coal.bean.FactoryStaff;

import java.util.List;

public interface FactoryStaffMapper {

    /***
     * 获得工厂中的全部员工
     * @param fac_id 工厂id
     * @return List
     */
    List<FactoryStaff> getAllStall(int fac_id);

    int addNewStaff(FactoryStaff factoryStaff);

    /***
     * 获得员工的实体信息
     * @param staff_id 员工id
     * @return 员工信息实体
     */
    FactoryStaff getStaffInfo(int staff_id);

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

}
