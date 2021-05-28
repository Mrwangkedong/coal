package com.example.coal.server;


import com.example.coal.Utils.AppPush;
import com.example.coal.Utils.DistanceUtil;
import com.example.coal.Utils.TimeUtils;
import com.example.coal.bean.*;
import com.example.coal.dao.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.*;

import static com.example.coal.server.DriverMsgServer.sqlsession;

@Service
public class DriverOrderServer {


    DriverOrderMapper mapper = sqlsession.getMapper(DriverOrderMapper.class);

    /**
     * 根据工厂id，获得双方工厂名称【卖家工厂，买家工厂，订单详情】
     * @param factory_orderid 工厂订单id
     * @return list
     */
    public Map<String,Object> getFacNames(int factory_orderid){
        String driOrderFF_name = mapper.getDriOrderFF_NAME(factory_orderid);
        String driOrderFT_name = mapper.getDriOrderFT_NAME(factory_orderid);
        Map<String ,Object> map = new HashMap<>();

//        获得订单详情
        FactoryOrder facOrderInfo = (FactoryOrder) new FacOrderServer().getFacOrderInfo(factory_orderid).get("facOrderInfo");
        //获得订单卖家
        int ff_id = facOrderInfo.getFf_id();
        FactoryMsg ff_facInfo = new FacMsgServer().getFacInfo(ff_id);
//        获得订单买家
        int ft_id = facOrderInfo.getFt_id();
        FactoryMsg ft_facInfo = new FacMsgServer().getFacInfo(ft_id);
        //获得卖家经纬度
        float ff_factory_longitude = ff_facInfo.getFactory_longitude();
        float ff_factory_latitude = ff_facInfo.getFactory_latitude();
        //获得买家经纬度
        float ft_factory_longitude = ft_facInfo.getFactory_longitude();
        float ft_factory_latitude = ft_facInfo.getFactory_latitude();
        //获得买家卖家之间的路线距离
        double distance = DistanceUtil.getDistance(ff_factory_longitude, ff_factory_latitude, ft_factory_longitude, ft_factory_latitude);
        map.put("ff_name",driOrderFF_name);
        map.put("ft_name",driOrderFT_name);
        map.put("facOrderInfo",facOrderInfo);
        map.put("distance",distance);

        return map;
    }

    /**
     * 获得司机订单中双方厂家的路线
     * @param driver_id
     * @return
     */
    public Map<String, Float> getOrderNowRoute(int driver_id){
        DriverOrderServer driverOrderServer = new DriverOrderServer();
        //获得当前订单信息
        DriverOrder orderNow = driverOrderServer.getOrderNow(driver_id);
        if (orderNow == null){
            return null;
        }
        //得到订单编号
        int factory_orderid = orderNow.getFactory_orderid();
        //定义map
        Map<String, Float> map = new HashMap<>();
        //获得订单详情
        FactoryOrder facOrderInfo = (FactoryOrder) new FacOrderServer().getFacOrderInfo(factory_orderid).get("facOrderInfo");

        //获得订单卖家
        int ff_id = facOrderInfo.getFf_id();
        FactoryMsg ff_facInfo = new FacMsgServer().getFacInfo(ff_id);
//        获得订单买家
        int ft_id = facOrderInfo.getFt_id();
        FactoryMsg ft_facInfo = new FacMsgServer().getFacInfo(ft_id);
        //获得卖家经纬度
        float ff_factory_longitude = ff_facInfo.getFactory_longitude();
        float ff_factory_latitude = ff_facInfo.getFactory_latitude();
        //获得买家经纬度
        float ft_factory_longitude = ft_facInfo.getFactory_longitude();
        float ft_factory_latitude = ft_facInfo.getFactory_latitude();

        map.put("ff_factory_longitude",ff_factory_longitude);
        map.put("ff_factory_latitude",ff_factory_latitude);
        map.put("ft_factory_longitude",ft_factory_longitude);
        map.put("ft_factory_latitude",ft_factory_latitude);

        return map;

    }

//

    /**
     * 获得司机的全部历史订单记录（订单时间，订单编号，金额，订单双方）
     * @param d_id 司机id
     * @return List
     */
    public List<Map<String ,Object>> getDriOrderList(int d_id){

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

//        1、获得全部的订单记录
        List<DriverOrder> driOrderList = mapper.getDriOrderList(d_id);
//        2、遍历，得到ff_id，ft_id，并找到他们的厂名
        for (DriverOrder driverOrder : driOrderList) {
//            2.1、获得工厂订单id
            int facorder_id = driverOrder.getFactory_orderid();
            Date order_date = driverOrder.getOrder_arriveffactorydate();
            int order_id = driverOrder.getId();
            float order_money = driverOrder.getOrder_money();
//            2.2、同过工厂订单id获得ff_name  ft_name
            String driOrderFF_name = mapper.getDriOrderFF_NAME(facorder_id);
            String driOrderFT_name = mapper.getDriOrderFT_NAME(facorder_id);
//            2.3、组合成map
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("order_date",order_date);
            map.put("order_money",order_money);
            map.put("ff_name",driOrderFF_name);
            map.put("ft_name",driOrderFT_name);
            map.put("order_id",order_id);
            maps.add(map);
        }
        return maps;

    }

    /***
     * 得到当前可以接受的订单
     * @return 订单List
     */
    public List<Map<String, Object>> getOrderWaitInfo(){
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        List<FactoryOrder> orderWaitInfo = mapper.getOrderWaitInfo();
        for (FactoryOrder factoryOrder : orderWaitInfo) {
            int fac_orderid = factoryOrder.getId();
            //  同过工厂订单id获得ff_name  ft_name
            String facOrderFF_name = mapper.getDriOrderFF_NAME(fac_orderid);
            String facOrderFT_name = mapper.getDriOrderFT_NAME(fac_orderid);
            //进行map映射
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("facOrderId",fac_orderid);
            map.put("facOrderGoodPrice",factoryOrder.getOrder_goodprice());
            map.put("facOrderCarClass",factoryOrder.getOrder_carclass());
            map.put("facOrderFF_name",facOrderFF_name);
            map.put("facOrderFT_name",facOrderFT_name);
            map.put("facOrderNeedCarNum",factoryOrder.getOrder_targetcarnum() - factoryOrder.getOrder_actualcarnum());
            map.put("facDistance",DistanceUtil.getDistanceByFacOrder(fac_orderid));
            maps.add(map);
        }
        return maps;
    }

    /***
     * 获得司机的当前订单
     * @param d_id 司机id
     * @return 当前司机的订单详细情况
     */
    public DriverOrder getOrderNow(int d_id){
        return mapper.getOrderNow(d_id);
    }


    /***
     * 根据订单id，获得司机订单的详细情况
     * @param order_id 司机订单id
     * @return 订单详细情况
     */
    public DriverOrder getDriOrderInfo(int order_id){
        try {
            System.out.println(mapper.getDriOrderInfo(order_id));
            return mapper.getDriOrderInfo(order_id);
        }catch (Exception e){
            return null;
        }
    }


    /****
     * 添加新的司机订单
     * @param map (driver_id,factory_orderid,order_ensuremoney,order_momey（在装货之后更改/在卸货之前改）,order_state-1)
     * @return 1：成功
     * 2：失败
     * 3：余额不足
     * 4：当前用户已存在订单
     * 5: 未绑定银行卡
     * 6： 车辆类型不符合
     */
    public int addDriOrderInfo(Map<String ,Object> map){
        //从map中获得信息
        Integer driver_id = (Integer) map.get("driver_id");
        int factory_orderid = (Integer) map.get("factory_orderid");
        //工厂订单mapper
        FacOrderMapper facOrderMapper = sqlsession.getMapper(FacOrderMapper.class);
        //得到工厂订单信息
        FactoryOrder facOrderInfo = facOrderMapper.getFacOrderInfo(factory_orderid);
        int order_carclass = facOrderInfo.getOrder_carclass();
        //获得司机信息
        DriverMsgMapper driverMsgMapper = sqlsession.getMapper(DriverMsgMapper.class);
        DriverMsg driverMsg = driverMsgMapper.getDriverMsg(driver_id);
        if (order_carclass != driverMsg.getDcard_carclass()){
            return 6;
        }

        //设置订单保证金
        float ensure_money;
        if (order_carclass == 1){
            ensure_money = (float) (0.2 * facOrderInfo.getOrder_goodprice() * 80);
            map.put("order_ensuremoney",ensure_money);
            map.put("order_money",facOrderInfo.getOrder_goodprice() * 80);
        }else {
            ensure_money = (float) (0.2 * facOrderInfo.getOrder_goodprice() * 45);
            map.put("order_ensuremoney",ensure_money);
            map.put("order_money",facOrderInfo.getOrder_goodprice() * 45);
        }


        //判断司机当前是否已经有了订单
        DriverOrder orderNow = new DriverOrderServer().getOrderNow(driver_id);
        if (orderNow != null){
            return 4;  //返回4，表示当前已经存在订单
        }

        //钱包Mapper
        UserWalletMapper userWalletMapper = sqlsession.getMapper(UserWalletMapper.class);
        UserWallet walletInfo = userWalletMapper.getWalletInfo(driver_id, 1);
        if (walletInfo == null){
            return 5;  //未绑定银行卡
        }
        if (walletInfo.getWallet_money() < ensure_money){
            return 3;   //返回3，说明余额不足
        }


        int facOrder_state = facOrderInfo.getOrder_state();
        //获得工厂订单现在实际车辆数 and 目标车辆数
        int order_actualcarnum = facOrderInfo.getOrder_actualcarnum();
        int order_targetcarnum = facOrderInfo.getOrder_targetcarnum();
        //如果实际车辆数错误，返回2
        if (order_actualcarnum >= order_targetcarnum || facOrder_state !=1){
            return 2;       //返回2，说明实际数量已经够了
        }else {
            //更改实际车辆数
            order_actualcarnum = order_actualcarnum + 1;
            facOrderInfo.setOrder_actualcarnum(order_actualcarnum);
            //进行账单信息及钱包信息的更改
            int i2 = addDriEnsureBill(ensure_money, driver_id, factory_orderid);
            //进行订单信息的更改
            int i1 = facOrderMapper.editFacOrder(facOrderInfo);
            //进行司机订单的添加
            int i = mapper.addDriOrderInfo(map);
            if (i1 == 1 & i == 1 & i2 == 1){
                sqlsession.commit();
                //判断增加之后是否已经接单完成
                if (order_actualcarnum == order_targetcarnum){
                 /*
                     在这里进行工厂订单结束接单的修改 1-->3
                */
                    facOrderInfo.setOrder_state(3);   //转化为接单完成/进行中
                    facOrderInfo.setOrder_endupdate(TimeUtils.getNowDate()); //注入当前时间
                    int i3 = facOrderMapper.editFacOrder(facOrderInfo);
                    if (i3==1){
                        sqlsession.commit();
                    }
                }
                return 1;
            }else {
                return 0;
            }
        }

    }

    /***
     * 在添加司机订单时候，进行账单的处理
     * @param ensure_money 保证金额
     * @param user_id 用户(司机)id
     * @return 返回是否成功
     */
    public int addDriEnsureBill(float ensure_money,int user_id,int facOrder_id){
        //更具facorder_id获得订单双方
        Map<String, Object> facNames = getFacNames(facOrder_id);
        String ff_name = (String) facNames.get("ff_name");
        String ft_name = (String) facNames.get("ft_name");

        UserBill userBill = new UserBill();
        userBill.setUser1_id(user_id);
        userBill.setBill_money(ensure_money);
        userBill.setBill_type(0);
        userBill.setUser2_name("支付保证金（"+ff_name+"-->"+ft_name+"）");
        java.util.Date date = new java.util.Date();          // 获取一个Date对象
        Timestamp timeStamp = new Timestamp(date.getTime());     //   讲日期时间转换为数据库中的timestamp类型
        userBill.setBill_data(timeStamp);
        userBill.setBill_state(1);

        //更改钱包信息
        int i = new UserWalletServer().editWalletMoney(user_id, 1, 0 - ensure_money);
        //增加账单信息
        int i1 = new UserBillServer().addUserBill(userBill);

        if (i1 == 1 & i == 1){
            return 1;
        }else {
            return 0;
        }


    }

    /****
     * 对司机的订单信息进行修改
     * @param driverOrder   订单实体
     * @return  1/0
     */
    public int editDriOrderInfo(DriverOrder driverOrder){
        int i = mapper.editDriOrderInfo(driverOrder);
        if (i == 1){
            sqlsession.commit();
        }
        return i;
    }

    /**
     * 修改到卖家厂时间，操作员，皮重等等
     * @param order_id 司机订单id
     * @param order_fedituserid1 操作员id
     * @param pz 皮重
     * @return 1/0
     */
    public int editDriOrderArriveFFInfo( int order_id,
                                         int order_fedituserid1, float pz){
        //        获得订单信息
        DriverOrder driOrderInfo = getDriOrderInfo(order_id);
        //获得当前时间
        Timestamp order_arriveffactorydate = TimeUtils.getNowDate();
        driOrderInfo.setOrder_arriveffactorydate(order_arriveffactorydate);
        //注入操作员id
        driOrderInfo.setOrder_fedituserid1(order_fedituserid1);
        //修改皮重
        driOrderInfo.setOrder_pz(pz);
        //修改状态
        driOrderInfo.setOrder_state(2);
        int i = mapper.editDriOrderInfo(driOrderInfo);

        //获得司机信息
        DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driOrderInfo.getDriver_id());

        if (i==1){
            AppPush.sendMessageAndroid(driverMsg.getD_cid(),"订单修改：皮重="+driOrderInfo.getOrder_pz());
            sqlsession.commit();
        }
        return i;
    }

    /**
     * 修改离开卖家厂时间，操作员，离厂毛重等等,同时修改工厂订单的order_actualweight
     * @param order_id 司机订单id
     * @param order_fedituserid2 操作员id
     * @param weightNow 现在重量
     * @return 1/0
     */
    public int editDriOrderLeaveFFInfo(@RequestParam int order_id,@RequestParam int order_fedituserid2,@RequestParam float weightNow){
        //        获得订单信息
        DriverOrder driOrderInfo = getDriOrderInfo(order_id);
        //注入id
        driOrderInfo.setId(order_id);
        //获得当前时间
        Timestamp order_leaveffactorydate = TimeUtils.getNowDate();
        //注入当前时间
        driOrderInfo.setOrder_leaveffactorydate(order_leaveffactorydate);
//        注入操作员id
        driOrderInfo.setOrder_fedituserid2(order_fedituserid2);
        //修改状态
        driOrderInfo.setOrder_state(3);
//        注入毛重1
        float mz = weightNow - driOrderInfo.getOrder_pz();
        driOrderInfo.setOrder_mz(mz);
//        司机订单修改
        int i1 = mapper.editDriOrderInfo(driOrderInfo);
        /*
        修改工厂订单order_actualweight
         */
        int factory_orderid = driOrderInfo.getFactory_orderid();
        FacOrderServer facOrderServer = new FacOrderServer();
        FactoryOrder facOrderInfo = (FactoryOrder) facOrderServer.getFacOrderInfo(factory_orderid).get("facOrderInfo");
        float order_actualweight = facOrderInfo.getOrder_actualweight();
        facOrderInfo.setOrder_actualweight(order_actualweight + mz);
        FacOrderMapper facOrderMapper = sqlsession.getMapper(FacOrderMapper.class);
        int i = facOrderMapper.editFacOrder(facOrderInfo);


        //获得司机信息
        DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driOrderInfo.getDriver_id());

        if (i==1 && i1==1){
            AppPush.sendMessageAndroid(driverMsg.getD_cid(),"订单修改：离场重量="+weightNow+"\t离场毛重="+driOrderInfo.getOrder_mz());
            sqlsession.commit();
            return 1;
        }else {
            return 0;
        }
    }


    /**
     * 工厂方查看司机订单信息
     * @param dri_orderId 司机订单id
     * @return 司机订单详细信息
     */
    public Map<String ,Object> getDriOrderAll(int dri_orderId){
        //通过订单id，获得订单
        DriverOrder driOrderInfo = mapper.getDriOrderInfo(dri_orderId);
        //通过订单信息，获得工厂订单一些信息[订单单价，双方工厂名称]
        int factory_orderid = driOrderInfo.getFactory_orderid();
        FactoryOrder facOrderInfo = (FactoryOrder) new FacOrderServer().getFacOrderInfo(factory_orderid).get("facOrderInfo");
        String ffName = (String) new FacOrderServer().getFacOrderInfo(factory_orderid).get("ff_name");
        String ftName = (String) new FacOrderServer().getFacOrderInfo(factory_orderid).get("ft_name");
        float order_goodprice = facOrderInfo.getOrder_goodprice();
        //通过订单信息，获得司机的一些信息【司机姓名，司机手机号码】
        int driver_id = driOrderInfo.getDriver_id();
        DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driver_id);
        String d_name = driverMsg.getD_name();
        String d_phonenum = driverMsg.getD_phonenum();
        //通过订单信息获得订单操作员姓名！
        String staffName1 = new FacStaffServer().getStaffName(driOrderInfo.getOrder_fedituserid1()); //称空车
        String staffName2 = new FacStaffServer().getStaffName(driOrderInfo.getOrder_fedituserid2()); //称出厂满车
        String staffName3 = new FacStaffServer().getStaffName(driOrderInfo.getOrder_tedituserid());  //称入场满车
        //新建map，进行添加
        Map<String,Object> map = new HashMap<>();
        map.put("ffName",ffName);
        map.put("ftName",ftName);
        map.put("d_name",d_name);
        map.put("d_phonenum",d_phonenum);
        map.put("order_goodprice",order_goodprice);
        map.put("staffName1",staffName1);
        map.put("staffName2",staffName2);
        map.put("staffName3",staffName3);
        map.put("driOrderInfo",driOrderInfo);
        return map;
    }

    /**
     * 修改到达买家工厂（结束）时间，操作员，到厂毛重,是否准时，评价星级，状态
     * @param order_id 订单id
     * @param order_tedituserid 操作员id
     * @param weightNow 当前重量
     * @param order_star 评价星级
     * @return
     */
    public int editDriOrderEndInfo( int order_id, int order_tedituserid,float weightNow, int order_star){
        //        获得订单信息
        DriverOrder driOrderInfo = new DriverOrderServer().getDriOrderInfo(order_id);
        //获得订单要求时间
        FactoryOrder facOrderInfo = (FactoryOrder)new FacOrderServer().getFacOrderInfo(driOrderInfo.getFactory_orderid()).get("facOrderInfo");
        int order_transporttime = facOrderInfo.getOrder_transporttime();
        //获得当前时间
        Timestamp order_enddate = TimeUtils.getNowDate();
        driOrderInfo.setOrder_enddate(order_enddate);
        //注入操作员id
        driOrderInfo.setOrder_tedituserid(order_tedituserid);
        //计算毛重2
        float mz2 = weightNow - driOrderInfo.getOrder_pz();
        driOrderInfo.setOrder_mz2(mz2);
        //评价星级
        driOrderInfo.setOrder_star(order_star);
        //计算小时差
        Timestamp orderEnddate = TimeUtils.getNowDate();  //当前时间
        Date order_startdate = driOrderInfo.getOrder_startdate();
        //注入是否准时
        driOrderInfo.setOrder_ontime(TimeUtils.ifOntime(orderEnddate,order_startdate,order_transporttime));
        //修改状态
        driOrderInfo.setOrder_state(0);
        //获得司机订单修改结果
        int i = mapper.editDriOrderInfo(driOrderInfo);
        /*
        修改工厂订单order_actualweight2，order_actualcarnum2
         */
        float order_actualweight = facOrderInfo.getOrder_actualweight2();
        int order_actualcarnum2 = facOrderInfo.getOrder_actualcarnum2();
        facOrderInfo.setOrder_actualweight2(order_actualweight + mz2);
        facOrderInfo.setOrder_actualcarnum2(order_actualcarnum2+1);
        FacOrderMapper facOrderMapper = sqlsession.getMapper(FacOrderMapper.class);
        int i1 = facOrderMapper.editFacOrder(facOrderInfo);
        /*
        进行资金转移
         */
        int driver_id = driOrderInfo.getDriver_id();
        new UserWalletServer().addWalletMoney(driver_id,1,facOrderInfo.getOrder_goodprice() * mz2,1);  //增加订单的钱
        new UserWalletServer().addWalletMoney(driver_id,1, (float) (facOrderInfo.getOrder_goodprice() * mz2 * 0.2),3);  //增加订单的保证金返回


        //获得司机信息
        DriverMsg driverMsg = new DriverMsgServer().getDriverMsg(driver_id);

        //返回
        if (i1 == 1 && i==1){
            AppPush.sendMessageAndroid(driverMsg.getD_cid(),"订单修改：离场重量="+weightNow+"\t到场毛重="+driOrderInfo.getOrder_mz2());
            sqlsession.commit();
            return 1;
        }else {
            return 0;
        }



    }







}
