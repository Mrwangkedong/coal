package com.example.coal.controller;

import com.example.coal.bean.FactoryMessage;
import com.example.coal.server.FacMessageServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Controller
@CrossOrigin(origins = "*",maxAge = 3600)
@EnableSwagger2
public class FacMessageController {

    @Autowired
    private FacMessageServer  facMessageServer;

    @ResponseBody
    @ApiOperation("新增新消息,向ft_fac")
    @PostMapping(path = "/addNewMessage")
    int addNewMessage(int from_id,int to_id){
        return facMessageServer.addNewMessage(from_id,to_id,"发起新的订单");
    }


    @ResponseBody
    @ApiOperation("修改消息状态")
    @PostMapping(path = "/editMessage")
    int editMessage(int messageId){
        return facMessageServer.editMessage(messageId);
    }


    @ResponseBody
    @ApiOperation("返回一个厂家的收到的未读消息")
    @PostMapping(path = "/getMegByToId")
    List<FactoryMessage> getMegByToId(int fac_id){
        return facMessageServer.getMegByToId(fac_id);
    }

    @ResponseBody
    @ApiOperation("返回一个厂家的收到的已读消息")
    @PostMapping(path = "/getMegByToId2")
    List<FactoryMessage> getMegByToId2(int fac_id){
        return facMessageServer.getMegByToId2(fac_id);
    }


}
