package com.example.coal.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MapUtils {

    public static String GPSCover(String coords, String commString) {

        String dest_url = "http://api.map.baidu.com/geoconv/v1/?coords="+coords+"&from=1&to=5&output=json&ak=fCP53ZyuQkjn4XVYM2qZNRYGI3s1kmYT";

        String rec_string = "";
        URL url = null;
        HttpURLConnection urlconn = null;
        OutputStream out = null;
        BufferedReader rd = null;
        try {
            url = new URL(dest_url);
            urlconn = (HttpURLConnection) url.openConnection();
            urlconn.setReadTimeout(1000 * 30);
            //urlconn.setRequestProperty("content-type", "text/html;charset=UTF-8");
            urlconn.setRequestMethod("POST");
            urlconn.setDoInput(true);
            urlconn.setDoOutput(true);
            out = urlconn.getOutputStream();
            out.write(commString.getBytes("UTF-8"));
            out.flush();
            out.close();
            rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1)
                sb.append((char) ch);
            rec_string = sb.toString();
        } catch (Exception e) {
            return "";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (urlconn != null) {
                    urlconn.disconnect();
                }
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e) {
                System.out.print("123");
            }
        }
        return rec_string;
    }


    /**
     * 逆地理编码 URL
     */
    final static String LONGITUDE_TO_ADDRESS_URL = "http://api.map.baidu.com/reverse_geocoding/v3/?output=json&coordtype=BD09&pois=1";
    final static String AK = "fCP53ZyuQkjn4XVYM2qZNRYGI3s1kmYT";

    /**
     * 逆地理编码
     * @param lng
     *        经度 113.325
     * @param lat
     *        纬度 23.1067,
     * @return
     */
    public static String longitudeToAddress(float lng, float lat ) {

        String url = LONGITUDE_TO_ADDRESS_URL + "&ak=" + AK + "&location=" + lat + "," + lng;
        HttpClient client = HttpClients.createDefault(); // 创建默认http连接
        HttpPost post = new HttpPost(url);// 创建一个post请求

        try {
            HttpResponse response = client.execute(post);// 用http连接去执行get请求并且获得http响应
            HttpEntity entity = response.getEntity();// 从response中取到响实体
            String html = EntityUtils.toString(entity);// 把响应实体转成文本
            return new JSONObject(new JSONObject(html).get("result").toString()).get("formatted_address").toString();
        } catch (Exception e) {
            System.out.println("逆地理编码[异常],");
        }
        return "";
    }


    public static void main(String[] args) throws JSONException {
        String coords = "106.6519570767,26.6245856997";
        String toAddress = longitudeToAddress(Float.parseFloat("103.984149"), Float.parseFloat("30.58585"));
        System.out.println(toAddress);
//        JSONObject jo = new JSONObject(s);  //转化为JSON
//        String result =GPSCover(coords,"");
//        System.out.println(result);
    }
}
