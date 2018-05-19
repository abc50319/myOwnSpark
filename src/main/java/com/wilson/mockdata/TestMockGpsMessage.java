package com.wilson.mockdata;


import com.clearspring.analytics.util.Lists;
import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import com.wilson.dategram.testInfoGPSGram;
import com.wilson.util.DateUtils;
import com.wilson.util.NumberUtils;
import kafka.producer.KeyedMessage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据，类型为cimId_terminalId_timeId_speed_satelliteNum_onlineStatus
 * 添加最后一个_onlineStatus
 */

public class TestMockGpsMessage {
    //发送对象数据
    public static String getRandomGPSMessageData(testInfoGPSGram testData){
        String cimId;
        String terminalId;
        String timeId;
        String speed;
        String satelliteNum;
        String onlineStatus;

        //testData.setCimId("41"+ String.valueOf(NumberUtils.getRandomInt(4)));
        testData.setCimId("410");
        testData.setTerminalId(String.valueOf(2000000+NumberUtils.getRandomInt(4000000)+1));
        testData.setTime("2017010210"+ String.valueOf(NumberUtils.getRandomInt(6)));
        testData.setSpeed(Double.parseDouble(String.valueOf(NumberUtils.getRandomInt(3))));
        testData.setSatelliteNum(Integer.parseInt(String.valueOf(NumberUtils.getRandomInt(7)-1)));
        //onlineStatus状态只会显示为0或者1
        testData.setOnlineStatus(Integer.parseInt(String.valueOf(NumberUtils.getRandomInt(2))));


        cimId = testData.getCimId();
        terminalId = testData.getTerminalId();
        timeId = testData.getTime();
        speed = String.valueOf(testData.getSpeed());
        satelliteNum = String.valueOf(testData.getSatelliteNum());
        onlineStatus= String.valueOf(testData.getOnlineStatus());
        return cimId+"_"+terminalId+"_"+timeId+"_"+speed+"_"+satelliteNum+"_"+onlineStatus;

    }


    //发送固定的列表数据
    public static List<String> getFixedList() throws ParseException {
        List<String> s1 = new ArrayList<String>();
        s1.add("411_1_20180329111_0_1_1");
        s1.add("410_1_20180329111_4_6_1");
        s1.add("410_1_20180329111_3_6_1");
        s1.add("410_1_20180329111_2_6_1");
        s1.add("410_1_20180329111_1_2_0");
        s1.add("410_1_20180329111_0_-1_0");
        s1.add("410_1_20180329111_0_2_0");
        s1.add("410_1_20180329111_0_-1_0");
        s1.add("410_1_20180329111_0_2_0");
        s1.add("410_1_20180329111_0_2_0");
        return s1;
    }


    //发送固定的列表数据List<KeyedMessage<String, String>>
    public static List<KeyedMessage<String, String>> getFixedList2() throws ParseException {
        List<KeyedMessage<String, String>> messageList = Lists.newArrayList();
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965918_1__"+ String.valueOf(DateUtils.getNowTenminuteStart()+1000)+"_1_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965918_1__"+ String.valueOf(DateUtils.getNowTenminuteStart()+1000)+"_1_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965918_1__"+ String.valueOf(DateUtils.getNowTenminuteStart()+3000)+"_0_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965919_1__"+ String.valueOf(DateUtils.getNowTenminuteStart()+3000)+"_1_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965919_1__"+ String.valueOf(DateUtils.getNowTenminuteStart()+5000)+"_0_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965920_2__"+ String.valueOf(DateUtils.getNowTenminuteStart()-6000)+"_1_1"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965920_2__"+ String.valueOf(DateUtils.getNowTenminuteStart()+3000)+"_0_0"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965921_3__"+ String.valueOf(DateUtils.getNowTenminuteStart()-6000)+"_1_1"));
        messageList.add(new KeyedMessage<String, String>(ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)
                , "151965922_4__"+ String.valueOf(DateUtils.getNowTenminuteStart()+1000)+"_1_0"));
        return messageList;
    }

}
