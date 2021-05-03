package com.example.coal.server;

import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.FactoryStaff;
import com.example.coal.dao.DriverMsgMapper;
import com.example.coal.dao.FactoryStaffMapper;
import com.example.coal.dao.UserBillMapper;

import java.util.HashMap;
import java.util.Map;

import static com.example.coal.server.DriverMsgServer.sqlsession;


public class LoginServe {

   FactoryStaffMapper factoryStaffMapper = sqlsession.getMapper(FactoryStaffMapper.class);
   DriverMsgMapper driverMsgMapper = sqlsession.getMapper(DriverMsgMapper.class);

   /**
    *工厂管理员登录
    * @param phoneNum 手机号
    * @param password 密码
    * @return 1/0
    */
   public Map<String ,Object> FacLogin(String phoneNum,String password){
      Map<String ,Object> map = new HashMap<>();
      try{
         FactoryStaff factoryStaff = factoryStaffMapper.getStaffInfoByPhone(phoneNum);
         String staff_password = factoryStaff.getStaff_password();
         if (password.equals(staff_password)){
            map.put("code",1);
            map.put("staff_id",factoryStaff.getId());
            map.put("fac_id",factoryStaff.getFactory_id());
            map.put("staff_class",factoryStaff.getStaff_class());
         }else {
            map.put("code",0);
         }
      }catch (Exception e){
         map.put("code",-1);
         System.out.println(e);
      }
      return map;
   }

   /**
    * 司机手机端登录
    * @param phoneNum 手机号
    * @param password 密码
    * @return Map
    */
   public Map<String, Object> DriverLogin(String phoneNum, String password) {
      Map<String ,Object> map = new HashMap<>();
      try{
         DriverMsg driverMsg = driverMsgMapper.getDriverMsg2(phoneNum);
         String d_password = driverMsg.getD_password();
         if (password.equals(d_password)){
            map.put("code",1);
            map.put("d_id",driverMsg.getId());
         }else {
            map.put("code",0);
         }
      }catch (Exception e){
         map.put("code",-1);
      }

      return map;

   }



   public static void main(String[] args) {
      System.out.println(new LoginServe().FacLogin("12312312312", "123"));
   }
}