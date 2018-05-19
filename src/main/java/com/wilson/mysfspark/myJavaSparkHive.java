/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wilson.mysfspark;

// $example on:spark_hive$

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.sql.*;
// $example off:spark_hive$

public class myJavaSparkHive {


  public static void main(String[] args) {

      SparkConf sparkConf = new SparkConf();
      sparkConf.setAppName("Spark SQL to Hive")
              .set("hive.metastore.uris", "thrift://:9083")
              .set("spark.sql.autoBroadcastJoinThreshold", "-1")
              .set("spark.port.maxRetries", "500")
              .setMaster("local");

      SparkSession spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate();
      spark.sql("desc dw_inc_ubas.test_lsm");
      //spark.sql("select * from dw_inc_ubas.test_lsm");

      spark.stop();
  }

}
