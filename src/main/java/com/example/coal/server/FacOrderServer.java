package com.example.coal.server;

import com.example.coal.Utils.DistanceUtil;
import com.example.coal.Utils.MapUtils;
import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.DriverMsg;
import com.example.coal.bean.DriverOrder;
import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryOrder;
import com.example.coal.dao.DriverOrderMapper;
import com.example.coal.dao.FacOrderMapper;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ClassUtils;

import javax.jws.Oneway;

import static com.example.coal.server.DriverMsgServer.sqlsession;

import java.util.*;

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
        //订单添加
        int i = mapper.addNewFacOrder(factoryOrder);
        /*
        进行资金的减少  i4
         */
        float orderMoney = factoryOrder.getOrder_goodprice() * factoryOrder.getOrder_targetweight();
        int i4 = new UserWalletServer().reduceWalletMoney(factoryOrder.getFf_id(), 2, orderMoney, 1);
        if (i4 == 2 || i4==3){
            return i4;          //如果未绑定银行卡或者是余额不足的直接返回
        }
        /*
        进行message通知  i3
         */
        int i3 = new FacMessageServer().addNewMessage(factoryOrder.getFf_id(), factoryOrder.getFt_id(), "发起新的订单");
        if (i==1 && i3==1 && i4 ==1){
            sqlsession.commit();
            return 1;
        }else
            return 0;
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
        /*
         * 此处应有转账
         */

        //进行消息通知
        int i = new FacMessageServer().addNewMessage(facOrderInfo.getFt_id(), facOrderInfo.getFf_id(), "接受发起的订单");
        //进行工厂订单修改
        int i2 = mapper.editFacOrder(facOrderInfo);
        if (i == 1 && i2 == 1){
            sqlsession.commit();
            return 1;
        }else {
            return 0;
        }
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
        //进行消息通知
        int i = new FacMessageServer().addNewMessage(facOrderInfo.getFt_id(), facOrderInfo.getFf_id(), "拒绝发起的订单");

        /*
        进行资金的减少  i4
         */
        float orderMoney = facOrderInfo.getOrder_goodprice() * facOrderInfo.getOrder_targetweight();
        int i3 = new UserWalletServer().addWalletMoney(facOrderInfo.getFf_id(), 2, orderMoney, 1);

        int i2 = mapper.editFacOrder(facOrderInfo);
        if (i == 1 && i2 == 1 && i3==1){
            sqlsession.commit();
            return 1;
        }else {
            return 0;
        }

    }

    /**
     * 工厂订单结束
     * @param fac_orderID 工厂订单id
     * @return 1/0  2-->车辆没有全部结束
     */
    public int facOrderOver(int fac_orderID){
        //通过工厂订单id获得工厂订单的具体信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderID);
        int order_actualcarnum = facOrderInfo.getOrder_actualcarnum();
        int order_actualcarnum2 = facOrderInfo.getOrder_actualcarnum2();
        if (order_actualcarnum != order_actualcarnum2){
            return 2;    //表示车辆还没有全部结束
        }
        /*
        进行资金周转
         */
//        获得weight2，weight1
        float order_actualweight = facOrderInfo.getOrder_actualweight();
        float order_actualweight2 = facOrderInfo.getOrder_actualweight2();
        float order_goodprice = facOrderInfo.getOrder_goodprice();
        int ff_id = facOrderInfo.getFf_id();
        if (order_actualweight2 < order_actualweight){
            new UserWalletServer().addWalletMoney(ff_id,2,order_goodprice * (order_actualweight - order_actualweight2),1);
        }
        //进行订单状态转换
        facOrderInfo.setOrder_state(0);
        int i = mapper.editFacOrder(facOrderInfo);
        if (i==1)
            sqlsession.commit();
        return i;
    }

    /***
     * 获得工厂订单全部信息【包括子订单】
     * @param fac_orderID 工厂订单id
     * @return
     */
    public Map<String,Object> getFacOrderGPS(int fac_orderID){
        FacMsgServer facMsgServer = new FacMsgServer();
//       获得具体订单信息
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(fac_orderID);
        //获得双方工厂
        int ff_id = facOrderInfo.getFf_id();
        int ft_id = facOrderInfo.getFt_id();
        //更具fac_id获得工厂的具体信息
        FactoryMsg ff_facInfo = new FacMsgServer().getFacInfo(ff_id);
        FactoryMsg ft_facInfo = new FacMsgServer().getFacInfo(ft_id);
        //获得ff的经纬度
        float ff_longitude = ff_facInfo.getFactory_longitude();
        float ff_latitude = ff_facInfo.getFactory_latitude();
        //获得ft的经纬度
        float ft_longitude = ft_facInfo.getFactory_longitude();
        float ft_latitude = ft_facInfo.getFactory_latitude();
        Map<String,Object> map = new HashMap<>();
        //添加经纬度到map中
        map.put("ff_longitude",ff_longitude);map.put("ff_latitude",ff_latitude);
        map.put("ft_longitude",ft_longitude);map.put("ft_latitude",ft_latitude);
        return map;
    }

    /***
     * 获得工厂订单的子订单列表【司机姓名，司机手机号，订单id，订单经纬度，当前所在地，当前状态，距离始发地/目的地距离】
     * @param fac_orderId 工厂订单id
     */
    public List<Map<String ,Object>> getFacOrderSonOrder(int fac_orderId) {
        //定义List
        List<Map<String ,Object>> sonOrderList = new ArrayList<>();
        //获得List<DriverOrder>
        List<DriverOrder> facOrderSonOrders = sqlsession.getMapper(DriverOrderMapper.class).getFacOrderSonOrder(fac_orderId);
        //进行facOrderSonOrders遍历
        for (DriverOrder facOrderSonOrder : facOrderSonOrders) {
            //获得子订单id，司机id
            int dri_orderId = facOrderSonOrder.getId();
            int driver_id = facOrderSonOrder.getDriver_id();
            //通过子订单司机id获得司机姓名，电话号码
            DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driver_id);
            String d_name = driverMsg.getD_name();
            String d_phonenum = driverMsg.getD_phonenum();
            //获得经纬度,当前所在地
            float order_longitude = facOrderSonOrder.getOrder_longitude();
            float order_latitude = facOrderSonOrder.getOrder_latitude();
            String address = MapUtils.longitudeToAddress(order_longitude, order_latitude);  //转化所在地
            //获得子订单状态
            int order_state = facOrderSonOrder.getOrder_state();
            //获得子订单接单时间
            Date order_startdate = facOrderSonOrder.getOrder_startdate();
            //通过司机订单，获得当前位置距离双方工厂的距离
            List<Float> distanceToFacs = getDistanceToFacs(facOrderSonOrder);
            Float FTDistance = distanceToFacs.get(0);
            Float FFDistance = distanceToFacs.get(1);
            //新建map
            Map<String,Object> map = new HashMap<>();
            map.put("d_orderId",dri_orderId);
            map.put("d_name",d_name);
            map.put("d_phoneNum",d_phonenum);
            map.put("d_address",address);
            map.put("order_longitude",order_longitude);
            map.put("order_latitude",order_latitude);
            map.put("d_state",order_state);
            map.put("order_startdate",order_startdate);
            if (order_state == 1){
                map.put("d_distance",FFDistance);
            }else if (order_state == 2){
                map.put("d_distance",FTDistance);
            }else if (order_state == 3){
                map.put("d_distance",FTDistance);
            }else {
                String s = "已送达";
                map.put("d_distance",s);
            }
            sonOrderList.add(map);
        }

        return sonOrderList;
    }

    /**
     * 返回司机订单所处位置距离双方工厂的距离
     * @param driverOrder 司机订单
     * @return 【距离买家工厂距离，距离卖家工厂距离】
     */
    public List<Float> getDistanceToFacs(DriverOrder driverOrder){
        //获得订单经纬度
        float driOrder_longitude = driverOrder.getOrder_longitude();
        float driOrder_latitude = driverOrder.getOrder_latitude();
        //通过司机订单获得工厂订单详情
        int factory_orderid = driverOrder.getFactory_orderid();
        FactoryOrder facOrderInfo = mapper.getFacOrderInfo(factory_orderid);
        //通过工厂订单获得双方工厂id
        int ff_id = facOrderInfo.getFf_id();
        int ft_id = facOrderInfo.getFt_id();
        //通过ff_id获得工厂信息并获得距离
        FactoryMsg ff_facInfo = new FacMsgServer().getFacInfo(ff_id);
        float ff_longitude = ff_facInfo.getFactory_longitude();
        float ff_latitude = ff_facInfo.getFactory_latitude();
        float distanceToFF = DistanceUtil.getDistance(driOrder_longitude, driOrder_latitude, ff_longitude, ff_latitude);
        //通过ft_id获得工厂信息并获得距离
        FactoryMsg ft_facInfo = new FacMsgServer().getFacInfo(ft_id);
        float ft_longitude = ft_facInfo.getFactory_longitude();
        float ft_latitude = ft_facInfo.getFactory_latitude();
        float distanceToFT = DistanceUtil.getDistance(driOrder_longitude, driOrder_latitude, ft_longitude, ft_latitude);
        List<Float> floatList = new ArrayList<>();
        floatList.add(distanceToFT);
        floatList.add(distanceToFF);
        return floatList;
    }

    /***
     * 获得工厂订单的子订单列表【司机姓名，司机手机号，订单id，订单经纬度，当前所在地，当前状态，距离始发地/目的地距离】
     * @param fac_orderId 工厂订单id
     */
    public List<Map<String,Object>> getFacOrderSonOrderEd(int fac_orderId){
        //定义List
        List<Map<String ,Object>> sonOrderList = new ArrayList<>();
        //获得List<DriverOrder>
        List<DriverOrder> facOrderSonOrders = sqlsession.getMapper(DriverOrderMapper.class).getFacOrderSonOrder(fac_orderId);
        //获得订单运费单价
        FactoryOrder facOrderInfo = (FactoryOrder) getFacOrderInfo(fac_orderId).get("facOrderInfo");
        float facorder_goodprice = facOrderInfo.getOrder_goodprice();
        //进行facOrderSonOrders遍历
        for (DriverOrder facOrderSonOrder : facOrderSonOrders) {
            //获得子订单id，司机id
            int dri_orderId = facOrderSonOrder.getDriver_id();
            int driver_id = facOrderSonOrder.getDriver_id();
            //通过子订单司机id获得司机姓名，电话号码
            DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driver_id);
            String d_name = driverMsg.getD_name();
            String d_phonenum = driverMsg.getD_phonenum();
            //获得子订单接单时间,订单完成时间
            Date order_startdate = facOrderSonOrder.getOrder_startdate();
            Date order_enddate = facOrderSonOrder.getOrder_enddate();
            //获得离厂[始]毛重，到厂[终]毛重
            float order_mz1 = facOrderSonOrder.getOrder_mz();
            float order_mz2 = facOrderSonOrder.getOrder_mz2();
            //获得是否准时
            int order_ontime = facOrderSonOrder.getOrder_ontime();
            //获得订单运费单价/总金额
            float order_money = facOrderSonOrder.getOrder_money();
            //新建map
            Map<String,Object> map = new HashMap<>();
            map.put("d_orderId",dri_orderId);
            map.put("d_name",d_name);
            map.put("d_phoneNum",d_phonenum);
            map.put("order_startdate",order_startdate);
            map.put("order_enddate",order_enddate);
            map.put("order_mz1",order_mz1);
            map.put("order_mz2",order_mz2);
            map.put("order_ontime",order_ontime);
            map.put("facorder_goodprice",facorder_goodprice);
            map.put("order_money",order_money);
            sonOrderList.add(map);
        }

        return sonOrderList;
    }




}
