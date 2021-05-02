package com.example.coal.controller;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryQualified;
import com.example.coal.server.FacMsgServer;
import com.example.coal.server.FacQuaServer;
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

    @PostMapping(value = "/upimg")
    @ResponseBody
    public Map<String,Object> userupimg(@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request) throws IOException {



        String name = (String)request.getParameter("name");
        //输出接收到的name
        System.out.println(name);
        Map<String,Object> map = new HashMap<>();
//        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = "111222333";
//        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String ext = "png";
        String filenames = filename + "." + ext;
        String pathname = "G:\\imgs\\" + filenames;
        file.transferTo(new File(pathname));
        map.put("src",filenames);
        map.put("code",0);
        return map;
    }


}
