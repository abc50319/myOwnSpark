package com.wilson.gpssparkstreaming;

import com.wilson.gpssparkstreaming.differentdatasource.GPSLogAnalyzeByTenMinutes;
import com.wilson.gpssparkstreaming.differentdatasource.GPSMessageAnalyzeByTenMinutes;
import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import com.wilson.gpssparkstreaming.differenttimetype.GpsAnalyzeByDay;
import com.wilson.gpssparkstreaming.differenttimetype.GpsAnalyzeByHour;
import com.wilson.util.DateUtils;
import com.wilson.util.SparkUtils;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;
import scala.concurrent.duration.Duration;

import java.text.ParseException;
import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.DAYS;


/**
 * describe:实时分析GPS消息数据
 * 数据接入:kafka,使用测试数据
 * 数据加工：spark streaming流处理
 * 数据同步:hbase,写入"bd_spark_gps_tenMinutes"表
 * author by wilson
 * date:2018-01-22 16:36:00
 *
 */
public class TrackAnalyzeSparkProject {
	static JavaStreamingContext jssc = null;
	//static ActorSystem mySystem = ActorSystem.create("TrackAnalyzeSparkSystem");

	public static void main(String[] args) throws ParseException, InterruptedException {

		//step0 处理小时和天级别数据
		//dealHourDayGPS();

		//step1 初始化javastreamingContext
		jssc = getSparkStreamingConf();

		//step2 从kafka接入数据,要么是<cimId_terminalId_timeId_speed_satelliteNum_onlineStatus>,要么是<serialNum_terminalId__timeId_onlineStatus>
		JavaDStream<String> substrDStream = getInitialDataFromKafka();

		//substrDStream.persist(StorageLevel.MEMORY_AND_DISK_SER());
		//设置窗口函数，收集十分钟的数据，间隔十分钟       //.window(Durations.seconds(600),Durations.seconds(600))

		//第一个大模块：实时处理十分钟级别GPS消息数据
		GPSMessageAnalyzeByTenMinutes.dealGPSMessageTenMinutes(substrDStream);

		//第二个大模块：实时处理十分钟级别GPS日志数据
		GPSLogAnalyzeByTenMinutes.dealGPSLogTenMinutes(substrDStream);

		jssc.start();
		jssc.awaitTermination();
		jssc.close();
	}

/*    //处理小时和天维度的代码
    private static void dealHourDayGPS() throws ParseException {
        long nowTimeSecond = DateUtils.getNowTimeSecond();
        //step1 设置小时执行的参数,每个小时整点执行
        long nextHourSecond = DateUtils.getNextSharpHourSecond();
        long hourDelayTime = nextHourSecond - nowTimeSecond;
        System.out.println("**************hourDelayTime:"+hourDelayTime);

        //step2 设置天执行的参数,每天整点执行
        long nextDaySecond = DateUtils.getNextSharpDay();
        long dayDelayTime = nextDaySecond - nowTimeSecond;
        System.out.println("=============dayDelayTime:"+dayDelayTime);

        //小时级别定时
        mySystem.scheduler().schedule(
                Duration.create(hourDelayTime, SECONDS)                    //延迟执行时间
                , Duration.create(1, HOURS)                                 //循环执行时间
                ,new Runnable() {
                    @Override
                    public void run() {
                        GpsAnalyzeByHour.dealGpsByHour();

                    }
                }
                , mySystem.dispatcher());

        //天级别定时器
        mySystem.scheduler().schedule(
                Duration.create(dayDelayTime, SECONDS)
                , Duration.create(1, DAYS)
                ,new Runnable() {
                    @Override
                    public void run() {
                        GpsAnalyzeByDay.dealGpsByDay();
                    }
                }
                , mySystem.dispatcher());
    }*/

	//初始化streamingContext
	private static JavaStreamingContext getSparkStreamingConf() {

		SparkConf sparkConf = new SparkConf()
				.set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
				.set("spark.streaming.backpressure.enabled","true")
				.set("spark.streaming.stopGracefullyOnShutdown","true")
				.set("spark.speculation","true")
				//.set("spark.streaming.unpersist","true")
				.setAppName(ConfigurationManager.getProperty(Constants.SPARK_APP_NAME));

		SparkUtils.setMaster(sparkConf);

		JavaStreamingContext jsscontext = new JavaStreamingContext(sparkConf,
				Durations.seconds(Long.parseLong(ConfigurationManager.getProperty(Constants.SPARK_BATCH_INTERVAL))));

		//jsscontext.checkpoint(ConfigurationManager.getProperty(Constants.SPARK_CHECKPOINT));
		jsscontext.sparkContext().setLogLevel("WARN");

		return jsscontext;
	}

	//从kafka接入数据
	private static JavaDStream<String> getInitialDataFromKafka() {
		//配置kafka相关的参数
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list",
				ConfigurationManager.getProperty(Constants.KAFKA_METADATA_BROKER_LIST));
		kafkaParams.put("auto.offset.reset", "largest");
		kafkaParams.put("group.id", "gpsMessageGroup");

		//"_"+ConfigurationManager.getProperty(Constants.KAFKA_TOPICS_OFFLINE)
		// 构建topic set
		String kafkaTopics = ConfigurationManager.getProperty(Constants.KAFKA_TOPICS)+","+ConfigurationManager.getProperty(Constants.KAFKA_TOPICS_OFFLINE);
		Set<String> topicsSet = new HashSet<>(Arrays.asList(kafkaTopics.split(",")));

		JavaPairInputDStream<String, String> kafkaGPSRMessageInputDstream = KafkaUtils.createDirectStream(
				jssc, String.class, String.class, StringDecoder.class, StringDecoder.class,
				kafkaParams, topicsSet);

		//针对有些数据会存在""，所以作出一个判断，如果有则去除
		return kafkaGPSRMessageInputDstream.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> v1) throws Exception {
				if (v1._2.startsWith("\"")) { return v1._2.substring(1, v1._2.length() - 1); }
				else { return v1._2; }
			}
		});

	}

    /*//初始化streamingContext,增加驱动器程序容错机制。当这段代码第一次执行时，会调用工厂函数把目录创建出来，此处需要设置检查点目录。
    //在驱动器程序失败之后，如果你重启驱动器并再次执行代码，getOrCreate()会重新从检查点目录中初始化StreamingContext,然后继续处理
    private static JavaStreamingContext getSparkStreamingConf1() {

        JavaStreamingContextFactory fact = new JavaStreamingContextFactory() {
            @Override
            public JavaStreamingContext create() {
                SparkConf sparkConf = new SparkConf()
                        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                        .set("spark.streaming.backpressure.enabled", "true")
                        .set("spark.streaming.stopGracefullyOnShutdown", "true")
                        .set("spark.speculation", "true")
                        .setAppName(ConfigurationManager.getProperty(Constants.SPARK_APP_NAME));

                SparkUtils.setMaster(sparkConf);

                JavaSparkContext scontext = new JavaSparkContext(sparkConf);
                JavaStreamingContext jsscontext = new JavaStreamingContext(scontext,
                        Durations.seconds(Long.parseLong(ConfigurationManager.getProperty(Constants.SPARK_BATCH_INTERVAL))));

                jsscontext.checkpoint(ConfigurationManager.getProperty(Constants.SPARK_CHECKPOINT));
                jsscontext.sparkContext().setLogLevel("WARN");
                return jsscontext;
            }
        };

        return JavaStreamingContext.getOrCreate(ConfigurationManager.getProperty(Constants.SPARK_CHECKPOINT),fact);

    }
    //初始化streamingContext*/

}
