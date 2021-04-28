package com.example.coal.Utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

public class DistanceUtil {

    public static float getDistance(float longitudeFrom, float latitudeFrom, float longitudeTo, float latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
        float ellipsoidalDistance = (float)new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();

        String res = String.format("%.2f", ellipsoidalDistance);
        return Float.parseFloat(res);
    }
}
