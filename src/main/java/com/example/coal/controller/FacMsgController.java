package com.example.coal.controller;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryQualified;
import com.example.coal.bean.FactoryStaff;
import com.example.coal.server.FacMsgServer;
import com.example.coal.server.FacQuaServer;
import com.example.coal.server.FacStaffServer;
import org.springframework.util.ClassUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableSwagger2
@CrossOrigin(origins = "*",maxAge = 3600)
public class FacMsgController {

    FacMsgServer facMsgServer = new FacMsgServer();
    FacQuaServer facQuaServer = new FacQuaServer();

    @ResponseBody
    @ApiOperation("获得所有工厂名称")
    @GetMapping(path = "/getFacNames")
    public List<String> getFacNames(){
        return facMsgServer.getFacNames();
    }

    @ResponseBody
    @ApiOperation("获得工厂的全部信息")
    @PostMapping(path = "/getFacMsg")
    FactoryMsg getFacMsg(@RequestParam @ApiParam("工厂id") int fac_id){
        return facMsgServer.getFacInfo(fac_id);
    }

    @ResponseBody
    @ApiOperation("更改工厂信息的非图片部分")
    @PostMapping(path = "/editFacMsgInfo")
    int editFacMsgInfo(@RequestParam int fac_id,@RequestParam String facName,@RequestParam String factory_address,@RequestParam String factory_lpname,
        float factory_longitude,@RequestParam float factory_latitude,@RequestParam String factory_lpcardnum){
//        1.根据fac_id获得信息实体
        FactoryQualified factoryQualified = facQuaServer.exitFacQuaByFacId(fac_id);
        //2.赋予实体值
        factoryQualified.setName(facName);
        factoryQualified.setFactory_address(factory_address);
        factoryQualified.setFactory_lpname(factory_lpname);
        factoryQualified.setFactory_longitude(factory_longitude);
        factoryQualified.setFactory_latitude(factory_latitude);
        //3.返回更新信息1/0
        return facQuaServer.editFacQualified(factoryQualified);
    }


    @ResponseBody
    @ApiOperation("上传身份证（正）")
    @PostMapping(path = "/editFacLpcardPhoto1")
    Map<String,Object> editFacLpcardPhoto1(@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request) throws IOException {
        int fac_id = Integer.parseInt(request.getParameter("fac_id"));
        String pathStatic = "G:\\coal\\src\\main\\resources\\static";
        // 图片存储目录及图片名称（存数据库的）
        String url_path = "qualified" + File.separator + "facImg" + File.separator + "lPcardPhoto1" + File.separator + String.valueOf(fac_id)+".png";
        //图片保存路径
        String savePath = pathStatic + File.separator + url_path;

//        0.进行FacQualified信息的更新
//        1.根据fac_id获得信息实体
        FactoryQualified factoryQualified = facQuaServer.exitFacQuaByFacId(fac_id);
//        2.进行信息更新
        factoryQualified.setFactory_lpcardphoto1(url_path);
        int i = facQuaServer.editFacQualified(factoryQualified);
        //存储返回数据
        Map<String,Object> map = new HashMap<>();
        //进行文件存储
        file.transferTo(new File(savePath));
        map.put("code",i);
        return map;
    }

    @ResponseBody
    @ApiOperation("上传身份证（反）")
    @PostMapping(path = "/editFacLpcardPhoto2")
    Map<String,Object> editFacLpcardPhoto2(@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request) throws IOException {
        int fac_id = Integer.parseInt(request.getParameter("fac_id"));
        String pathStatic = "G:\\coal\\src\\main\\resources\\static";
        // 图片存储目录及图片名称（存数据库的）
        String url_path = "qualified" + File.separator + "facImg" + File.separator + "lPcardPhoto2" + File.separator + String.valueOf(fac_id)+".png";
        //图片保存路径
        String savePath = pathStatic + File.separator + url_path;
//        0.进行FacQualified信息的更新
//        1.根据fac_id获得信息实体
        FactoryQualified factoryQualified = facQuaServer.exitFacQuaByFacId(fac_id);
//        2.进行信息更新
        factoryQualified.setFactory_lpcardphoto2(url_path);
        int i = facQuaServer.editFacQualified(factoryQualified);
        //存储返回数据
        Map<String,Object> map = new HashMap<>();
        //进行文件存储
        file.transferTo(new File(savePath));
        map.put("code",i);
        return map;
    }

    @ResponseBody
    @ApiOperation("上传营业资格")
    @PostMapping(path = "/editFacLicencePhoto")
    Map<String,Object> editFacLicencePhoto(@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request) throws IOException {
        int fac_id = Integer.parseInt(request.getParameter("fac_id"));
        String pathStatic = "G:\\coal\\src\\main\\resources\\static";
        // 图片存储目录及图片名称（存数据库的）
        String url_path = "qualified" + File.separator + "facImg" + File.separator + "licencePhoto" + File.separator + String.valueOf(fac_id)+".png";
        //图片保存路径
        String savePath = pathStatic + File.separator + url_path;
//        0.进行FacQualified信息的更新
//        1.根据fac_id获得信息实体
        FactoryQualified factoryQualified = facQuaServer.exitFacQuaByFacId(fac_id);
//        2.进行信息更新
        factoryQualified.setFactory_licencephoto(url_path);
        int i = facQuaServer.editFacQualified(factoryQualified);
        //存储返回数据
        Map<String,Object> map = new HashMap<>();
        //进行文件存储
        file.transferTo(new File(savePath));
        map.put("code",i);
        return map;
    }

    @ResponseBody
    @ApiOperation("根据工厂id获得工厂名称")
    @PostMapping(path = "/getFacNameById")
    public String getFacNameById(@RequestParam int fac_id){
        return facMsgServer.getFacNameById(fac_id);
    }

    @ResponseBody
    @ApiOperation("新增工厂申请")
    @PostMapping("/addNewFactory")
    public int addNewFactory(MultipartFile[] facImg,HttpServletRequest request) throws IOException {

        String facName = request.getParameter("facName");
        String factory_lpname = request.getParameter("factory_lpname");
        String factory_lpcardnum = request.getParameter("factory_lpcardnum");
        float factory_longitude = Float.parseFloat(request.getParameter("factory_longitude"));
        float factory_latitude = Float.parseFloat(request.getParameter("factory_latitude"));
        String factory_address = request.getParameter("factory_address");
        int fac_id = facMsgServer.addFacInfo(facName, factory_lpname, factory_lpcardnum, factory_longitude, factory_latitude, factory_address);
        if (fac_id == 0 || fac_id == 2){
            return fac_id;
        }
        /*
        添加工厂管理员员工！！！！！
         */
        String manage_name = request.getParameter("manage_name");
        String manage_phoneNum = request.getParameter("manage_phoneNum");
        String manage_password = request.getParameter("manage_password");
        FactoryStaff factoryStaff = new FactoryStaff();
        factoryStaff.setStaff_name(manage_name);
        factoryStaff.setFactory_id(fac_id);
        factoryStaff.setStaff_phonenum(manage_phoneNum);
        factoryStaff.setStaff_password(manage_password);
        factoryStaff.setStaff_class(1);
        int i1 = new FacStaffServer().addNewStaff(factoryStaff);
        if (i1==0){
            return 0;
        }
        /*
         * 返回得到的id，添加照片到指定路径
         */
        String pathStatic = "G:\\coal\\src\\main\\resources\\static";
        // 图片存储目录及图片名称（存数据库的）、图片保存路径(营业许可)
        String url_path_licencePhoto = "qualified" + File.separator + "facImg" + File.separator + "licencePhoto" + File.separator + String.valueOf(fac_id)+".png";
        String savePath_url_path_licencePhoto = pathStatic + File.separator + url_path_licencePhoto;
        facImg[0].transferTo(new File(savePath_url_path_licencePhoto));
        //        （身份证正面）
        String url_path_lPcardPhoto1 = "qualified" + File.separator + "facImg" + File.separator + "lPcardPhoto1" + File.separator + String.valueOf(fac_id)+".png";
        String savePath_url_path_lPcardPhoto1 = pathStatic + File.separator + url_path_lPcardPhoto1;
        facImg[1].transferTo(new File(savePath_url_path_lPcardPhoto1));
        //         （身份证反面）
        String url_path_lPcardPhoto2 = "qualified" + File.separator + "facImg" + File.separator + "lPcardPhoto2" + File.separator + String.valueOf(fac_id)+".png";
        String savePath_url_path_lPcardPhoto2 = pathStatic + File.separator + url_path_lPcardPhoto2;
        facImg[2].transferTo(new File(savePath_url_path_lPcardPhoto2));
        FactoryMsg facInfo = facMsgServer.getFacInfo(fac_id);
        facInfo.setFactory_licencephoto(url_path_licencePhoto);
        facInfo.setFactory_lpcardphoto1(url_path_lPcardPhoto1);
        facInfo.setFactory_lpcardphoto2(url_path_lPcardPhoto2);
        int i = facMsgServer.editFacMsg(facInfo);
        if (i==0){
            return i;
        }

        return 1;
    }


}
