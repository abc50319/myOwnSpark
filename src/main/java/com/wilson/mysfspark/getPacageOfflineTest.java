package com.wilson.mysfspark;

import com.sf.fvp.ConvertUtil;
import com.sf.fvp.dto.FactRouteDto1;
import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import com.wilson.util.SparkUtils;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;


/**
 * spark core离线计算某站点半小时段的到件量
 * 数据处理策略：处理数据分为两个部分，第一个部分是在一个duration中连接和关闭配对的，这一块容易计算;
 *             第二个部分是一组连接和关闭是跨duration的。这一块比较难处理，具体处理策略如下：
 *             将没有配对的数据重新发送到kafka,下一个duration再处理，直到配对成功。
 *             注意：为了方便计算，引入一个标志位，来判断是否是当前duration的数据。
 * 数据接入:kafka,使用测试数据
 * 数据加工：spark streaming流处理
 * 数据同步:hbase,写入"bd_spark_gpslog_tenMinutes"表
 * author by wilson
 * date:2018-02-27 17:00:00
 *
 */
public class getPacageOfflineTest {
	static JavaStreamingContext jssc = null;
	public static void main(String[] args) throws InterruptedException {

		//step1 初始化javastreamingContext
        jssc=getSparkStreamingConf();

		//step2 从kafka接入数据,获取标准数据<opcode(操作码)_zonecode(操作网点)_destzonecode(目的地代码)>
		JavaDStream<String> substrDStream = getInitialDataFromKafka();

		JavaPairDStream<String, String> getObjeDStream = substrDStream.mapToPair(new PairFunction<String, String, String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, String> call(String s) throws Exception {
				if(s.isEmpty()){
					return null;
				}
				FactRouteDto1 factRouteDto = ConvertUtil.fromByte(FactRouteDto1.class, s.getBytes());
				System.out.println("*************************factRouteDto.info**************************"+factRouteDto.basictoString());
				return new Tuple2<String, String>(factRouteDto.getBarRecordId()+"_"+factRouteDto.getWaybillNo(), factRouteDto.getZoneCode());
			}
		});

		getObjeDStream.print();


		/*substrDStream.foreachRDD(new VoidFunction<JavaRDD<String>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(JavaRDD<String> stringJavaRDD) throws Exception {
				stringJavaRDD.foreachPartition(new VoidFunction<Iterator<String>>() {
					private static final long serialVersionUID = 1L;
					@Override
					public void call(Iterator<String> stringIterator) throws Exception {
						if(stringIterator.hasNext()) {
							while (stringIterator.hasNext()) {
								//RouteInferDto routeInferDto = JsonUtil.getObject(stringIterator,RouteInferDto.class);

								FactRouteDto factRouteDto = ConvertUtil.fromByte(FactRouteDto.class, stringIterator.next().getBytes());
								System.out.println("***********************************factRouteDto" + factRouteDto.toString());
								//System.out.println("stringIterator" + stringIterator.next().getBytes());

							}
						}
					}
				});
			}
		});*/

		jssc.start();
		jssc.awaitTermination();
		jssc.close();
	}


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
		kafkaParams.put("group.id", "sfGroup");

		//"_"+ConfigurationManager.getProperty(Constants.KAFKA_TOPICS_OFFLINE)
		// 构建topic set
		String kafkaTopics = ConfigurationManager.getProperty(Constants.KAFKA_TOPICS);
		Set<String> topicsSet = new HashSet<>(Arrays.asList(kafkaTopics.split(",")));

		JavaPairInputDStream<String, String> kafkaGPSRMessageInputDstream = KafkaUtils.createDirectStream(
				jssc, String.class, String.class, StringDecoder.class, StringDecoder.class,
				kafkaParams, topicsSet);

		//针对有些数据会存在""，所以作出一个判断，如果有则去除
		return kafkaGPSRMessageInputDstream.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> v1) throws Exception {
				return v1._2;
			}
		});

	}



}
