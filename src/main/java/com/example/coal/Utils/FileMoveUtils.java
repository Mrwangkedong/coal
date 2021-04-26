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


}
