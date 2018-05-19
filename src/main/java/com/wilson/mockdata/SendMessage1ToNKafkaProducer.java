package com.wilson.mockdata;

import kafka.producer.KeyedMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.wilson.util.KafkaProducer;

/**
 *测试offset功能，持续发送数据，有1到N
 */
public class SendMessage1ToNKafkaProducer {


    public static void main(String[] args) {
        //创建kafka的生产者类
        KafkaProducer kafkaProducer = KafkaProducer.getInstance("118.89.59.251:9092,118.89.60.46:9092,118.89.62.210:9092");

        while (true) {
            final CountDownLatch latchOne = new CountDownLatch(10);// 使用java并发库concurrent
            ExecutorService newCachedThreadPool = Executors
                    .newCachedThreadPool();

            //发送数据过程，可以选择多少个进程进行发送数据
            for(int i =0;i<1;i++){
                //
                sendMessage(i,kafkaProducer,newCachedThreadPool,latchOne);
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


    //发送1到N的数据
    public  static void sendMessage(final int i, final com.wilson.util.KafkaProducer producer, ExecutorService newCachedThreadPool, final CountDownLatch latchOne){
        newCachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //发送数据，数据源是1到MAX值
                for(Long i=0L;i<Long.MAX_VALUE;i++) {
                    producer.send(new KeyedMessage<String, String>(
                            "flume_test_lsm", i.toString()));

                    if(i%10000==0){
                        try {
                            Thread.sleep(3* 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    /**/
                }
                latchOne.countDown();
            }
        });
    }

}
