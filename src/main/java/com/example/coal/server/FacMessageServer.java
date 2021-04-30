package com.example.coal.server;

import com.example.coal.Utils.TimeUtils;
import com.example.coal.bean.FactoryMessage;
import com.example.coal.bean.FactoryMsg;
import com.example.coal.dao.FacMessageMapper;
import com.example.coal.dao.FacMsgMapper;

import java.sql.Timestamp;
import java.util.List;

import static com.example.coal.server.DriverMsgServer.sqlsession;

public class FacMessageServer {
    FacMessageMapper mapper = sqlsession.getMapper(FacMessageMapper.class);

    /**
     * 增加新的消息
     * @param from_id 开启订单厂家
     * @return 1/0
     */
    public int addNewMessage(int from_id,int to_id,String content){
        FactoryMessage factoryMessage = new FactoryMessage();
        factoryMessage.setFrom_id(from_id);      //发送厂家id
        factoryMessage.setTo_id(to_id);   //收到厂家id
        factoryMessage.setContent(content);
        Timestamp nowDate = TimeUtils.getNowDate();
        factoryMessage.setMessageDate(nowDate); //当前时间
        //根据id获得厂家名称
        FactoryMsg facInfo = new FacMsgServer().getFacInfo(from_id);
        String from_name = facInfo.getName();
        factoryMessage.setFrom_name(from_name);

        int i = mapper.addNewMessage(factoryMessage);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

    /**
     * 修改消息状态
     * @param messageId 消息id
     * messageState 消息状态,自动有1-0
     * @return 1/0
     */
    public int editMessage(int messageId){
        int i = mapper.editMessage(messageId);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

    /**
     * 返回一个厂家的收到的未读消息
     * @param fac_id 工厂id
     * @return List
     */
    public List<FactoryMessage> getMegByToId(int fac_id){
        return mapper.getMegByToId(fac_id);
    }

    /**
     * 返回一个厂家的收到的已读消息
     * @param fac_id 工厂id
     * @return List
     */
    public List<FactoryMessage> getMegByToId2(int fac_id){
        return mapper.getMegByToId2(fac_id);
    }

}
