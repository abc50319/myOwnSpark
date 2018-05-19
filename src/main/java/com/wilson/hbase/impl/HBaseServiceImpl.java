package com.wilson.hbase.impl;


import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import com.wilson.hbase.HBaseService;
import com.wilson.util.HBaseUtil;
import com.wilson.util.ThreadPoolUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * HBaseService Mutator 实现类
 * Created by babylon on 2016/12/5.
 */
public class HBaseServiceImpl implements HBaseService {

    private static final Logger logger = LoggerFactory.getLogger(HBaseServiceImpl.class);

    private ThreadPoolUtil threadPool= ThreadPoolUtil.init();       // 初始化线程池

    private static Configuration hbaseconfig = null;

    static {
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum","testmaster,testslave01,testslave02");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("hbase.rootdir",  ConfigurationManager.getProperty(Constants.HBASE_ROOTDIR));

        hbaseconfig = HBaseConfiguration.create(conf);

    }

    /**
     * 多线程同步提交
     * @param tableName  表名称
     * @param puts  待提交参数
     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
     */
    public void batchPut(final String tableName, final List<Put> puts, boolean waiting) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HBaseUtil.put(tableName, puts);
                } catch (Exception e) {
                    logger.error("batchPut failed . ", e);
                }
            }
        });

        if(waiting){
            try {
                threadPool.awaitTermination();
            } catch (InterruptedException e) {
                logger.error("HBase put job thread pool await termination time out.", e);
            }
        }
    }




}
