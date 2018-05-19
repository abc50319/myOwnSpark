package com.wilson.hive;

public class SparkToHive {
            /*
        //step5 将输出数据输出到和hive中    出现问题

        //<terminalId_timeId,采集频次_总点数_在线点数_弱信号点数_GPS为未启点数_速度为0点数>
        //转换成terminalId_timeId_采集频次_总点数_在线点数_弱信号点数_GPS为未启点数_速度为0点数
        JavaDStream<String> mergeFinalFormatOutputDStream = finalFormatOutputDStream.map(new Function<Tuple2<String, String>, String>() {
            @Override
            public String call(Tuple2<String, String> stringStringTuple2) throws Exception {
                return stringStringTuple2._1 + "_" + stringStringTuple2._2;
            }
        });

        finalFormatOutputDStream.foreachRDD(new Function<JavaPairRDD<String, String>, Void>() {
            @Override
            public Void call(JavaPairRDD<String, String> stringStringJavaPairRDD) throws Exception {


                JavaRDD<Row> gpsRowRdd = stringStringJavaPairRDD.map(new Function<Tuple2<String, String>, Row>() {
                    @Override
                    public Row call(Tuple2<String, String> stringStringTuple2) throws Exception {
                        String terminalId = stringStringTuple2._1.split("-")[0];
                        String timeId = stringStringTuple2._1.split("-")[1];
                        String frequency = stringStringTuple2._2.split("_")[0];
                        String totalNum = stringStringTuple2._2.split("_")[1];
                        String onlineNum = stringStringTuple2._2.split("_")[2];
                        String weakNum = stringStringTuple2._2.split("_")[3];
                        String notOpenGpsNum = stringStringTuple2._2.split("_")[4];
                        String zeroSpeedNum = stringStringTuple2._2.split("_")[5];

                        return RowFactory.create(
                                terminalId, timeId, frequency, totalNum, onlineNum
                                , weakNum, notOpenGpsNum, zeroSpeedNum);
                    }
                });

                //将数据插入hive中
                //insert2tHive(sqlContext,gpsRowRdd);
                return null;
            }
        });



         private static void insert2tHive(HiveContext sqlContext,JavaRDD<Row> javaRDD){
        List<StructField> structFields = Arrays.asList(
                DataTypes.createStructField("terminalId", DataTypes.StringType, true),
                DataTypes.createStructField("timeId", DataTypes.StringType, true),
                DataTypes.createStructField("frequency", DataTypes.StringType, true),
                DataTypes.createStructField("totalNum", DataTypes.StringType, true),
                DataTypes.createStructField("onlineNum", DataTypes.StringType, true),
                DataTypes.createStructField("weakNum", DataTypes.StringType, true),
                DataTypes.createStructField("notOpenGpsNum", DataTypes.StringType, true),
                DataTypes.createStructField("zeroSpeedNum", DataTypes.StringType, true));
        StructType structType = DataTypes.createStructType(structFields);
        DataFrame finalDF = sqlContext.createDataFrame(javaRDD,structType);
        finalDF.saveAsTable("BD_SPARK_GPS_TENMINUTES");
    }









*/
    //stringStringJavaPairDStream.print();
}
