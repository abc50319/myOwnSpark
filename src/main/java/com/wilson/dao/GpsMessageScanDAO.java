package com.wilson.dao;



import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;


public interface GpsMessageScanDAO {

    /**
     * 从Hbase中根据filter获取数据
     * @param
     * @return 返回从hbase中查询到的数据，并且转化成为JavaPairRDD<ImmutableBytesWritable, Result>
     */
    JavaPairRDD<ImmutableBytesWritable, Result> scanByFilter(String tableName, JavaSparkContext javaSparkContext,String filterStr);

}
