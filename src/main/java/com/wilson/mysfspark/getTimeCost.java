package com.wilson.mysfspark;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * function: 根据源仓库和目的仓库，计算耗时
 * 输入：源仓库和目的仓库
 * 返回：耗时
 */
public class getTimeCost {
    public static void  main(String[] args) throws IOException {
        System.out.print("just a test!");
        String sourceX="106.695458";
        String sourceY="26.463696";
        String destX="106.850216";
        String desty="27.563959";
        System.out.println(getTimeCostFunc(sourceX,sourceY,destX,desty));
    }

    public static int getTimeCostFunc(String sourceX,String sourceY,String destX,String desty) throws IOException {


        int timeCost;
        String baseUrl="http://gis-rss-new.intsit.sfdc.com.cn:1080/rp";
        String routeUrl=baseUrl+"?x1="+sourceX+"&y1="+sourceY
                +"&x2="+destX+"&y2="+desty+"&type=0&cc=1&Vehicle=2&ak=1b20896414c79752d47e27839b3f5f63";
        System.out.println("************************************"+routeUrl);

        /*
         * 以下内容为HttpClient建立连接的一般用法,使用HttpClient建立客户端
         * 使用get方法访问指定URL获得应答
         */
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(routeUrl);
        CloseableHttpResponse response = client.execute(get);

        /*
         * 以下内容为将HTML内容转化为String
         * 获得应答体,将应答体转为String格式，此处使用了EntityUtils中的toString方法，编码格式设置为"utf-8"
         * 完成后关闭客户端与应答
         * */
        HttpEntity entity = response.getEntity();
        String content;
        if (entity != null) {
            content = EntityUtils.toString(entity, "utf-8");
            client.close();
            response.close();
            System.out.println("***content***"+content);
            String[] split = content.split(",");
            if(split[split.length-2].contains("time")){
                timeCost=Integer.parseInt(split[split.length-2].split(":")[1])/60;
                return timeCost;
            }
            return -1;

        }else{
            return -1;
        }

    }
}
