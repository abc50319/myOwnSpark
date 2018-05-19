package com.wilson.mockdata;


import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMessageListKafkaProducer {


    public static void main(String[] args) {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "118.89.59.251:9092,118.89.60.46:9092 ,118.89.62.210:9092");
        props.put("bootstrap.servers", "118.89.59.251:9092,118.89.60.46:9092,118.89.62.210:9092");
        //The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
        //“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
        props.put("acks", "all");
        //如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
        props.put("retries", 0);

        //The producer maintains buffers of unsent records for each partition.
        props.put("batch.size", 16384);
        //默认立即发送，这里这是延时毫秒数
        props.put("linger.ms", 1);
        //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
        props.put("buffer.memory", 33554432);
        //The key.serializer and value.serializer instruct how to turn the key and value objects the user provides with their ProducerRecord into bytes.
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建kafka的生产者类
        final Producer<String, String> producer = new KafkaProducer<String, String>(props);

        while (true) {
            final CountDownLatch latchOne = new CountDownLatch(10);// 使用java并发库concurrent
            ExecutorService newCachedThreadPool = Executors
                    .newCachedThreadPool();

            //发送数据过程，可以选择多少个进程进行发送数据
            for(int i =0;i<1;i++){
                //
                sendMessage(i,producer,newCachedThreadPool,latchOne);
            }

            try {
                latchOne.await();
            } catch (InterruptedException e) {
            }

            try {
                Thread.sleep(5* 1000);
            } catch (InterruptedException e) {
            }

        }

    }

    //发送一个固定列表的数据
    //特点是每一条发送到kafka的数据是有""的
    public  static void sendMessage(final int i, final Producer<String, String> producer
            , ExecutorService newCachedThreadPool, final CountDownLatch latchOne){
        newCachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //发送的数据源
                    List<String> strList = TestMockGpsTotal.getFixedList();
                    for(String s1:strList){
                        String json = JSONObject.toJSONString(s1);
                        producer.send(new ProducerRecord<String, String>("flume_test_lsm", s1));
                        //producer.send(new ProducerRecord<String, String>("flume_test_lsm", json));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                latchOne.countDown();
            }
        });
    }




/*	//发送一个固定列表的数据
	//特点是每一条发送到kafka的数据是没有""的
	public  static void sendMessage2(final int i, final com.wilson.util.KafkaProducer producer
			, ExecutorService newCachedThreadPool, final CountDownLatch latchOne){
		newCachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					//发送的数据源
					producer.send(TestMockGpsLog.getFixedList2());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				latchOne.countDown();
			}
		});
	}*/

}
