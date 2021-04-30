package com.example.coal.dao;

import com.example.coal.bean.FactoryMessage;

import java.util.List;

public interface FacMessageMapper {
    /**
     * 增加新的消息
     * @param factoryMessage 消息实体
     * @return 1/0
     */
    int addNewMessage(FactoryMessage factoryMessage);

    /**
     * 修改消息状态
     * @param messageId 消息id
     * messageState 消息状态,自动有1-0
     * @return 1/0
     */
    int editMessage(int messageId);

    /**
     * 返回一个厂家的收到的未读消息
     * @param fac_id 工厂id
     * @return
     */
    List<FactoryMessage> getMegByToId(int fac_id);

    /**
     * 返回一个厂家的收到的已读消息
     * @param fac_id 工厂id
     * @return
     */
    List<FactoryMessage> getMegByToId2(int fac_id);



}
