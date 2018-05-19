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
package com.learnHive;

// $example on:spark_hive$

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.sql.*;
import java.util.Properties;
// $example off:spark_hive$

public class myJavaOperateHive {


  public static void main(String[] args) throws SQLException {

   String url = "jdbc:hive2://10.202.77.200:10000/dw_inc_ubas";
    try {
      Class.forName("org.apache.hive.jdbc.HiveDriver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
      Connection conn = DriverManager.getConnection(url,"hive","hive");
      Statement stmt = conn.createStatement();
      String querySQL = "select * from dw_inc_ubas.test_lsm";
      ResultSet res = stmt.executeQuery(querySQL);

      while (res.next()) {
          System.out.println(res.getRow());
      }


      //spark.sql("desc ods_kafka_fvp. fvp_core_fact_route;");




    //spark.stop();
  }
  private static void runJdbcDatasetExample(SparkSession spark) {
    // $example on:jdbc_dataset$
    // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
    // Loading data from a JDBC source
    Dataset<Row> jdbcDF = spark.read()
            .format("jdbc")
            .option("url", "jdbc:hive2://10.116.59.25:10000/gdl")
            .option("dbtable", "a")
            .option("user", "01369839")
            .option("password", "0w836m53i58e")
            .load();

    jdbcDF.show();


  }
}
