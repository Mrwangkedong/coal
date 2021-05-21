package com.example.coal.Utils;

import com.example.coal.bean.FactoryMsg;
import com.example.coal.bean.FactoryOrder;
import com.example.coal.server.FacMsgServer;
import com.example.coal.server.FacOrderServer;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.Map;

public class DistanceUtil {

    public static float getDistance(float longitudeFrom, float latitudeFrom, float longitudeTo, float latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
        float ellipsoidalDistance = (float)new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();

        String res = String.format("%.2f", ellipsoidalDistance);
        return Float.parseFloat(res);
    }


    /**
     * 根据工厂订单id。获得工厂间距离
     * @param facOrder_id 工厂id
     * @return distance
     */
    public static float getDistanceByFacOrder(int facOrder_id){
        FactoryOrder facOrderInfo = (FactoryOrder) new FacOrderServer().getFacOrderInfo(facOrder_id).get("facOrderInfo");
        FactoryMsg ffInfo = new FacMsgServer().getFacInfo(facOrderInfo.getFf_id());
        FactoryMsg ftInfo = new FacMsgServer().getFacInfo(facOrderInfo.getFt_id());

        return getDistance(ffInfo.getFactory_longitude(),ffInfo.getFactory_latitude(),ftInfo.getFactory_longitude(),ftInfo.getFactory_latitude());

    }

}
