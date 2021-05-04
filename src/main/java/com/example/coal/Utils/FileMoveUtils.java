package com.example.coal.Utils;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileMoveUtils {

    /***
     * 文件移动
     * @param srcPathStr 原始地址
     * @param desPathStr 目的地址
     * @return 1/0
     */
    public static int fileMove(String srcPathStr, String desPathStr){
        try {
            Files.move(Paths.get(srcPathStr),Paths.get(desPathStr), StandardCopyOption.REPLACE_EXISTING);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    public static int facPhotoMove(int fac_id){
        String lPcardPhoto1_before = "G:\\coalstatic\\qualified\\facImg\\lPcardPhoto1\\"+fac_id+".png";
        String lPcardPhoto2_before = "G:\\coalstatic\\qualified\\facImg\\lPcardPhoto2\\"+fac_id+".png";
        String licencePhoto_before = "G:\\coalstatic\\qualified\\facImg\\licencePhoto\\"+fac_id+".png";


        String lPcardPhoto1_after = "G:\\coalstatic\\facImg\\lPcardPhoto1\\"+fac_id+".png";
        String lPcardPhoto2_after = "G:\\coalstatic\\facImg\\lPcardPhoto2\\"+fac_id+".png";
        String licencePhoto_after = "G:\\coalstatic\\facImg\\licencePhoto\\"+fac_id+".png";

        try {
            fileMove(lPcardPhoto1_before, lPcardPhoto1_after);
            fileMove(lPcardPhoto2_before, lPcardPhoto2_after);
            fileMove(licencePhoto_before, licencePhoto_after);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public static void main(String[] args) {
        fileMove("G:\\coal\\src\\main\\resources\\static\\qualified\\facImg\\licencePhoto\\1.png","G:\\coal\\src\\main\\resources\\static\\facImg\\licencePhoto\\1.png");
    }

}
