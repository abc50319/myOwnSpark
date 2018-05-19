package com.wilson.hive;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.hive.HiveContext;

public class testSparkReadHive {

    public static void main(String[] args) {
        // 首先还是创建SparkConf
        SparkConf conf = new SparkConf()
                .setAppName("testSparkReadHive");
        // 创建JavaSparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 创建HiveContext，注意，这里，它接收的是SparkContext作为参数，不是JavaSparkContext
        HiveContext hiveContext = new HiveContext(sc.sc());

        // 第一个功能，使用HiveContext的sql()方法，可以执行Hive中能够执行的HiveQL语句


        DataFrame goodStudentsDF = hiveContext.sql("SELECT * from bd_ads_accumulated_data limit 100 ");

        goodStudentsDF.show();

        sc.close();
    }
}


