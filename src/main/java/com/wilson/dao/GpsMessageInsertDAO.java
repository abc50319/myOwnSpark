package com.wilson.dao;



import com.wilson.domain.GPSMessageOutput;

import java.io.IOException;
import java.util.List;

/**
 * 广告黑名单DAO接口
 * @author Administrator
 *
 */
public interface GpsMessageInsertDAO {

    /**
     * 批量插入十分钟级用户GPS消息
     * param gpsMessageOutputs
     */
    void insertBatch0(List<GPSMessageOutput> gpsMessageOutputs,String tableName) throws IOException;
    //void insertBatch3(List<GPSMessageOutput2> gpsMessageOutputs, String tableName) throws IOException;
    //void insertBatch4(List<GPSMessageOutput2> gpsMessageOutputs, String tableName) throws IOException;
    //void insertBatchTest(List<GPSMessageOutput> gpsMessageOutputs) throws IOException;
    //void insertBatchTest2(List<GPSMessageOutput> gpsMessageOutputs) throws IOException;
    //使用JDBCHBase helper来进行插入操作
    void insertBatch(String tableName,List<String> gpsMessageOutputs);


}
