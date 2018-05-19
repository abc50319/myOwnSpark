import com.sf.fvp.ConvertUtil;
import com.wilson.conf.ConfigurationManager;
import com.wilson.constant.Constants;
import org.junit.Test;

public class testUtils {

    @Test
    public void testConfig() {
        System.out.println("spark.app.name："+ConfigurationManager.getProperty(Constants.SPARK_APP_NAME));
        System.out.println("kafka.topics："+ConfigurationManager.getProperty(Constants.KAFKA_TOPICS));
        System.out.println("kafka.metadata.zkconnect："+ConfigurationManager.getProperty(Constants.KAFKA_METADATA_ZKCONNECT_LIST));
        System.out.println("kafka.metadata.broker.list："+ConfigurationManager.getProperty(Constants.KAFKA_METADATA_BROKER_LIST));
    }

    @Test
    public void test() {
        //ConvertUtil.fromByte();
        }

/*    spark.app.name=getkafkadata
    spark.batch.interval=15
    spark.checkpoint=

            #大数据测试外网broker
    kafka.metadata.broker.list=
            #大数据测试外网zookeeper
    kafka.metadata.zkconnect.list=10.202.24.5:2181,10.202.24.6:2181,10.202.24.7:2181/kafka/other2
#测试kafka的topic
    kafka.topics=FACT_ROUTE_HATEST_SIT_NEW*/
}
