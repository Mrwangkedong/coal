package com.example.coal.server;

import com.example.coal.Utils.MybatisUtils;
import com.example.coal.bean.DriverMsg;
import com.example.coal.dao.DriverMsgMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverMsgServer {
    //        获得sqlsession对象
    public static  SqlSession sqlsession = MybatisUtils.getSqlsession();
    private DriverMsgMapper mapper = sqlsession.getMapper(DriverMsgMapper.class);

//  判断某个司机id是否存在
    public boolean ifExitDriId(int d_id){

        List<Integer> driverIds = mapper.getDriverIds();
        for (Integer driverId : driverIds) {
            if (d_id == driverId){
                return true;
            }
        }
        return false;
    }

//   判断对应司机的id是否对应密码
    public boolean ifPwbTrue(int d_id,String password){

        String driverPwb = mapper.getDriverPwb(d_id);
        return driverPwb.equals(password);
    }


//    根据司机id获得司机的全部信息
    public DriverMsg getDriverMsg(int d_id){

        return mapper.getDriverMsg(d_id);
    }


//    更改司机信息
    public int editDriverMsg(DriverMsg driverMsg){
        int i = mapper.editDriverMsg(driverMsg);
        sqlsession.commit();
        return i;
    }


    /**
     * 获得所有的司机用户信息
     * @return List
     */
    public List<DriverMsg> getAllDriMsg(){
        return mapper.getDriMsgs();
    }

    /**
     * 添加新的司机用户（待审核）
     * @param driverImg 照片
     * @param driverMsg 司机信息
     * @return -1：被注册          1：成功          0：失败
     * @throws IOException
     */
    public Map<String,Object> addNewDriver(MultipartFile[] driverImg,DriverMsg driverMsg) throws IOException {
        Map<String,Object> map = new HashMap<>();

        if (driverImg.length != 4){
            map.put("code",-1);
            map.put("content","文件数量不正确...");
            return map;
        }

        String d_phonenum = driverMsg.getD_phonenum();
        DriverMsg driverMsg2 = mapper.getDriverMsg2(d_phonenum);
        if (driverMsg2 != null){
            map.put("code",-1);
            if (driverMsg2.getD_ifqualified() == 2){
                map.put("content","正在注册中...");
            }else {
                map.put("content","当前手机号已被注册...");
            }

            return map;
        }
        //进行添加新的司机用户
        int i = mapper.addNewDriver(driverMsg);
        if (i==1)
            sqlsession.commit();
        else{
            map.put("code",0);
            return map;
        }


        driverMsg2 = mapper.getDriverMsg2(d_phonenum);
        int d_id = driverMsg2.getId();
        /*
         * 返回得到的id，添加照片到指定路径
         */
        String pathStatic = "G:\\coalstatic\\";
        // 身份证（正）
        String url_path_pcardPhoto1 =  "driverImg" + File.separator + "pcard_photoaddress1" + File.separator + d_id+".png";
        String savePath_url_path_pcardPhoto1 = pathStatic + File.separator + url_path_pcardPhoto1;
        driverImg[0].transferTo(new File(savePath_url_path_pcardPhoto1));
        // 身份证（反）
        String url_path_pcardPhoto2 =  "driverImg" + File.separator + "pcard_photoaddress2" + File.separator + d_id+".png";
        String savePath_url_path_pcardPhoto2 = pathStatic + File.separator + url_path_pcardPhoto2;
        driverImg[1].transferTo(new File(savePath_url_path_pcardPhoto2));
        // 驾驶证
        String url_path_dcardPhoto =  "driverImg" + File.separator + "dcard_photo" + File.separator + d_id+".png";
        String savePath_url_path_dcardPhoto = pathStatic + File.separator + url_path_dcardPhoto;
        driverImg[2].transferTo(new File(savePath_url_path_dcardPhoto));
        // 车辆照片
        String url_path_carPhoto =  "driverImg" + File.separator + "dcard_carphoto" + File.separator + d_id+".png";
        String savePath_url_path_carPhoto = pathStatic + File.separator + url_path_carPhoto;
        driverImg[3].transferTo(new File(savePath_url_path_carPhoto));

        driverMsg2.setPcard_photoaddress1(url_path_pcardPhoto1);
        driverMsg2.setPcard_photoaddress2(url_path_pcardPhoto2);
        driverMsg2.setDcard_photo(url_path_dcardPhoto);
        driverMsg2.setDcard_carphoto(url_path_carPhoto);

        int i1 = mapper.editDriverMsg(driverMsg2);

        if (i1 == 1){
            sqlsession.commit();
            map.put("code",1);
        }else
            map.put("code",0);

        return map;


    }



}
