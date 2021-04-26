package com.example.coal.server;

import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.DriverOrder;
import com.example.coal.bean.FactoryOrder;
import com.example.coal.dao.DriverOrderMapper;
import com.example.coal.dao.FacOrderMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.ClassUtils;

import javax.jws.Oneway;

import static com.example.coal.server.DriverMsgServer.sqlsession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacOrderServer{


    FacOrderMapper mapper = sqlsession.getMapper(FacOrderMapper.class);

    /***
     * 通过工厂订单id，获得工厂订单的详细信息
     * @param fac_orderid 工厂订单id
     * @return 工厂订单详细信息
     */
    public Map<String,Object> getFacOrderInfo(int fac_orderid){
        FacMsgServer facMsgServer = new FacMsgServer();
        //获得双方工厂名称
        Map<String, Object> names = facMsgServer.getNamesByFacOrderId(fac_orderid);
        String ffName = (String) names.get("ffName");
        String ftName = (String) names.get("ftName");
//       获得具体订单信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderid);
//        实现map
        Map<String,Object> map = new HashMap<>();

        map.put("ft_name",ffName);
        map.put("ff_name",ftName);
        map.put("facOrderInfo",facOrderInfo);
        return map;
    }

    /**
     * 更改工厂订单信息
     * @param factoryOrder 工厂订单信息
     * @return 是否更改成功
     */
    public int editFacOrder(FactoryOrder factoryOrder){
        int i = mapper.editFacOrder(factoryOrder);
        if (i==1){
            sqlsession.commit();
            return 1;
        }else {
            return 0;
        }
    }



    /***
     * 获得历史订单（出）
     * @param fac_id 工厂订单id
     * @return 历史订单列表（出）
     */
    public List<Map<String,Object>> getFacOrderEd1(int fac_id){
        List<FactoryOrder> facOrderEdList1 = mapper.getFacOrderEd1(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderEdList1) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }


        return maps;
    }

    /***
     * 获得历史订单（入）
     * @param fac_id 工厂订单id
     * @return 历史订单列表（入）
     */
    public List<Map<String, Object>> getFacOrderEd2(int fac_id){
        List<FactoryOrder> facOrderEd2 = mapper.getFacOrderEd2(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderEd2) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }
        return maps;
    }

    /***
     * 获得等待被确认的订单（该工厂为卖家）
     * @param fac_id 工厂id
     * @return List<订单>
     * SQL语句：SELECT * from factory_order WHERE ff_id = #{fac_id} AND order_state = 2;
     */
    public List<Map<String, Object>> getFacOrderWait1(int fac_id){
        List<FactoryOrder> facOrderWait1 = mapper.getFacOrderWait1(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderWait1) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }
        return maps;
    }

    /***
     * 获得等待确认的订单（该工厂为买家）
     * @param fac_id 工厂id
     * @return List<订单>
     * SQL语句：SELECT * from factory_order WHERE ft_id = #{fac_id} AND order_state = 2;
     */
    public List<Map<String, Object>> getFacOrderWait2(int fac_id){
        List<FactoryOrder> facOrderWait2 = mapper.getFacOrderWait2(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderWait2) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }
        return maps;
    }


    /***
     * 获得正在进行中订单（入）
     * @param fac_id 工厂id
     * @return List
     */
    public List<Map<String, Object>> getFacOrderIng1(int fac_id){
        List<FactoryOrder> facOrderIng1 = mapper.getFacOrderIng1(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderIng1) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }
        return maps;
    }

    /***
     * 获得正在进行中订单（入）
     * @param fac_id 工厂id
     * @return List
     */
    public List<Map<String, Object>> getFacOrderIng2(int fac_id){
        List<FactoryOrder> facOrderIng2 = mapper.getFacOrderIng2(fac_id);
        //新建一个List
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //facOrderEdList1的循环
        for (FactoryOrder factoryOrder : facOrderIng2) {
            //新建一个map，用来保存每一条记录
            Map<String,Object> map = new HashMap<>();
            //获得fac_orderID
            int fac_orderID = factoryOrder.getId();
            //通过fac_orderID获得双方名称
            Map<String, Object> names = new FacMsgServer().getNamesByFacOrderId(fac_orderID);
            String ffName = (String) names.get("ffName");
            String ftName = (String) names.get("ftName");
            //将姓名，工厂订单信息加入map
            map.put("ffName",ffName);
            map.put("ftName",ftName);
            map.put("factoryOrder",factoryOrder);
            //将map加入到List中
            maps.add(map);
        }
        return maps;
    }

    /**
     * 添加新的工厂订单
     * @param factoryOrder 工厂实体信息
     * @return 1/0
     */
    public int addNewFacOrder(FactoryOrder factoryOrder){
        int i = mapper.addNewFacOrder(factoryOrder);
        if (i==1){
            sqlsession.commit();
        }
        return i;
    }


    /***
     * 工厂订单接受
     * @param fac_orderID 工厂订单id
     * @return 1/0
     */
    public int facOrderJieshou(int fac_orderID){
        //通过工厂订单id获得工厂订单的具体信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderID);
        //更改订单的状态信息,2(待确认)->1(接单/进行)
        facOrderInfo.setOrder_state(1);
        return new FacOrderServer().editFacOrder(facOrderInfo);
    }

    /***
     * 订单工厂拒绝
     * @param fac_orderID 工厂订单id
     * @return 1/0
     */
    public int facOrderJujue(int fac_orderID,String order_refuseReason){
        //通过工厂订单id获得工厂订单的具体信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderID);
        //更改订单的状态信息,2(待确认)->4(拒绝/进行)
        facOrderInfo.setOrder_state(4);   //订单状态
        facOrderInfo.setOrder_refuseReason(order_refuseReason);  //拒绝原因
        return new FacOrderServer().editFacOrder(facOrderInfo);
    }

    /***
     * 获得工厂订单全部信息【包括子订单】
     * @param fac_orderID 工厂订单id
     * @return
     */
    public Map<String,Object> getOrderIngInfo(int fac_orderID){
        //获得List<DriverOrder>
        List<DriverOrder> facOrderSonOrder = sqlsession.getMapper(DriverOrderMapper.class).getFacOrderSonOrder(fac_orderID);
        FacMsgServer facMsgServer = new FacMsgServer();
        //获得双方工厂名称
        Map<String, Object> names = facMsgServer.getNamesByFacOrderId(fac_orderID);
        String ffName = (String) names.get("ffName");
        String ftName = (String) names.get("ftName");
//       获得具体订单信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderID);
        Map<String,Object> map = new HashMap<>();
        map.put("ffName",ffName);
        map.put("ftName",ftName);
        map.put("facOrderInfo",facOrderInfo);
        map.put("SonOrderList",facOrderSonOrder);
        return map;
    }




    public static void main(String[] args) {
        String aStatic = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        System.out.println(aStatic);
    }



}
