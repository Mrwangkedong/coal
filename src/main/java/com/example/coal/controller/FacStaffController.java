package com.example.coal.controller;


import com.example.coal.bean.FactoryStaff;
import com.example.coal.server.FacStaffServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class FacStaffController {

    FacStaffServer facStaffServer = new FacStaffServer();

    @ResponseBody
    @PostMapping(path = "/getAllStaff")
    @ApiOperation("获得某个工厂下的全部员工信息")
    List<FactoryStaff> getAllStaff(@RequestParam int fac_id){
        return facStaffServer.getAllStaff(fac_id);
    }

    @ResponseBody
    @PostMapping(path = "/addNewStaff")
    @ApiOperation("添加新的员工信息")
    int addNewStaff(@RequestParam String staffName,@RequestParam String staffSex,@RequestParam String staffPhone,
                    @RequestParam String staffPcard,@RequestParam String birthday,@RequestParam String staffPlace,
                    @RequestParam int staff_factoryID,@RequestParam String staffClass,@RequestParam String staffPassword) throws ParseException {
        //处理接收的数据
        //处理性别
        if (staffSex.equals("0")){
            staffSex = "男";
        }else{
            staffSex = "女";
        }
        //处理职位
        int staff_Class = Integer.parseInt(staffClass);
        //新建一个FactoryStaff()
        FactoryStaff factoryStaff = new FactoryStaff();
//        添加职员姓名
        factoryStaff.setStaff_name(staffName);
//        添加性别
        factoryStaff.setStaff_sex(staffSex);
//        添加手机号码
        factoryStaff.setStaff_phonenum(staffPhone);
        //添加身份证号码
        factoryStaff.setStaff_pcrdnumber(staffPcard);
        //进行日期格式转化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        java.util.Date newDate=sdf.parse(birthday);
        factoryStaff.setStaff_birthday(newDate);
        //添加位置
        factoryStaff.setStaff_place(staffPlace);
        //添加员工类别
        factoryStaff.setStaff_class(staff_Class);
        //添加所在工厂id
        factoryStaff.setFactory_id(staff_factoryID);
//        添加密码
        factoryStaff.setStaff_password(staffPassword);
        return facStaffServer.addNewStaff(factoryStaff);

    }

    @ResponseBody
    @PostMapping(path = "/getStaffInfo")
    @ApiOperation("获得某个员工信息")
    FactoryStaff getStaffInfo(int staff_id){
        return facStaffServer.getStaffInfo(staff_id);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffPwb")
    @ApiOperation("更改员工密码")
    int editStaffPwb(@RequestParam int staff_id,@RequestParam String newPwb){
        return facStaffServer.editStaffPwb(staff_id,newPwb);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffBirthDay")
    @ApiOperation("更改员工出生日期")
    int editStaffBirthDay(@RequestParam int staff_id,@RequestParam String newBirthDay) throws ParseException {
        return facStaffServer.editStaffBirthday(staff_id,newBirthDay);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffPcardNum")
    @ApiOperation("更改员工身份证号码")
    int editStaffPcardNum(@RequestParam int staff_id,@RequestParam String newPcardNum){
        //根据id获得实体信息
        FactoryStaff staffInfo = facStaffServer.getStaffInfo(staff_id);
        //设置新的身份证信息
        staffInfo.setStaff_pcrdnumber(newPcardNum);
        return facStaffServer.editStaffInfo(staffInfo);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffStaffClass")
    @ApiOperation("更改员工类别")
    int editStaffStaffClass(@RequestParam int staff_id,@RequestParam int newStaffClass){
        //根据id获得实体信息
        FactoryStaff staffInfo = facStaffServer.getStaffInfo(staff_id);
        //设置新的员工类别
        staffInfo.setStaff_class(newStaffClass);
        return facStaffServer.editStaffInfo(staffInfo);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffPlace")
    @ApiOperation("更改员工住址")
    int editStaffPlace(@RequestParam int staff_id,@RequestParam String  newPlace){
        //根据id获得实体信息
        FactoryStaff staffInfo = facStaffServer.getStaffInfo(staff_id);
        //设置新的住址
        staffInfo.setStaff_place(newPlace);
        return facStaffServer.editStaffInfo(staffInfo);
    }

    @ResponseBody
    @PostMapping(path = "/editStaffPhoneNum")
    @ApiOperation("更改员工手机号")
    int editStaffPhoneNum(@RequestParam int staff_id,@RequestParam String  newPhoneNum){
        //根据id获得实体信息
        FactoryStaff staffInfo = facStaffServer.getStaffInfo(staff_id);
        //设置新的手机号
        staffInfo.setStaff_phonenum(newPhoneNum);
        return facStaffServer.editStaffInfo(staffInfo);
    }

    @ResponseBody
    @PostMapping(path = "/deleteStaffById")
    @ApiOperation("根据id删除员工")
    int deleteStaffById(@RequestParam int staff_id){
        return facStaffServer.deleteStaffInfo(staff_id);
    }

}
