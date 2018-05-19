package com.wilson.hbase.factory;



import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import com.wilson.hbase.HBaseService;
import com.wilson.hbase.impl.HBaseServiceImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;

import java.util.List;

/**
 * HBase 各个组件管理调用类
 * 可以根据配置文件来选择  HBase 官方 API 还是第三方API
 * Created by babylon on 2016/12/6.
 */
public class HBase {

    private HBase() {}

    private static Configuration hbaseconfig = null;
    private static HBaseService hBaseService;
    static {
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum","testmaster,testslave01,testslave02");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("hbase.rootdir",  ConfigurationManager.getProperty(Constants.HBASE_ROOTDIR));

        hbaseconfig = HBaseConfiguration.create(conf);
        // TODO  根据配置文件来选择  HBase 官方 API 还是第三方API
        hBaseService = new HBaseServiceImpl();
//        hBaseService = new AsyncHBaseServiceImpl();
    }


    /**
     * 多线程同步提交
     * @param tableName  表名称
     * @param puts  待提交参数
     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
     */
    public static void put(String tableName, List<Put> puts, boolean waiting) {
        hBaseService.batchPut(tableName, puts, waiting);
    }




}
