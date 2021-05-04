package com.example.coal.Utils;


import java.io.File;

public class FileMoveUtils {

    /***
     * 文件移动
     * @param srcPathStr 原始地址
     * @param desPathStr 目的地址
     * @return 1/0
     */
    public static int fileMove(String srcPathStr, String desPathStr){
        try {
            File source=new File(srcPathStr);
            File target=new File(desPathStr);
            source.renameTo(target);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static int facPhotoMove(int fac_id){
        String lPcardPhoto1_before = "G:\\coal\\src\\main\\resources\\static\\qualified\\facImg\\lPcardPhoto1\\"+fac_id+".png";
        String lPcardPhoto2_before = "G:\\coal\\src\\main\\resources\\static\\qualified\\facImg\\lPcardPhoto2\\"+fac_id+".png";
        String licencePhoto_before = "G:\\coal\\src\\main\\resources\\static\\qualified\\facImg\\licencePhoto\\"+fac_id+".png";


        String lPcardPhoto1_after = "G:\\coal\\src\\main\\resources\\static\\facImg\\lPcardPhoto1\\"+fac_id+".png";
        String lPcardPhoto2_after = "G:\\coal\\src\\main\\resources\\static\\facImg\\lPcardPhoto2\\"+fac_id+".png";
        String licencePhoto_after = "G:\\coal\\src\\main\\resources\\static\\facImg\\licencePhoto\\"+fac_id+".png";

        try {
            fileMove(lPcardPhoto1_before,lPcardPhoto1_after);
            fileMove(lPcardPhoto2_before,lPcardPhoto2_after);
            fileMove(licencePhoto_before,licencePhoto_after);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }


}
