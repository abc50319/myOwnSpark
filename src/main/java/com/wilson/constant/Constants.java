package com.wilson.constant;

/**
 * 常量接口
 * @author liushuming
 *
 */
public interface Constants {

    /**
     * 项目配置相关的常量
     */
    String SPARK_LOCAL = "spark.local";
    String SPARK_LOCAL_TASKID_SESSION = "spark.local.taskid.session";
    String SPARK_LOCAL_TASKID_PAGE = "spark.local.taskid.page";
    String SPARK_LOCAL_TASKID_PRODUCT = "spark.local.taskid.product";
    String SPARK_BATCH_INTERVAL = "spark.batch.interval";
    String SPARK_CHECKPOINT = "spark.checkpoint";

    /**
     * JDBC相关配置
     */
    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_DATASOURCE_SIZE = "jdbc.datasource.size";
    String JDBC_URL = "jdbc.url";
    String JDBC_USER = "jdbc.user";
    String JDBC_PASSWORD = "jdbc.password";
    String JDBC_URL_PROD = "jdbc.url.prod";
    String JDBC_USER_PROD = "jdbc.user.prod";
    String JDBC_PASSWORD_PROD = "jdbc.password.prod";

    /**
     * Kafka相关的配置
     */
    String KAFKA_METADATA_BROKER_LIST = "kafka.metadata.broker.list";
    String KAFKA_METADATA_ZKCONNECT_LIST = "kafka.metadata.zkconnect.list";
    String KAFKA_TOPICS = "kafka.topics";
    String KAFKA_TOPICS_OFFLINE ="kafka.topics.offline";

    /**
     * Spark作业相关的常量
     */
    String SPARK_APP_NAME_SESSION = "EcarUserVisitSessionAnalyzeSpark";
    String SPARK_APP_NAME = "spark.app.name";
    String FIELD_SESSION_ID = "sessionid";
    String FIELD_SEARCH_KEYWORDS = "searchKeywords";
    String FIELD_CLICK_CATEGORY_IDS = "clickCategoryIds";
    String FIELD_AGE = "age";
    String FIELD_PROFESSIONAL = "professional";
    String FIELD_CITY = "city";
    String FIELD_SEX = "sex";
    String FIELD_VISIT_LENGTH = "visitLength";
    String FIELD_STEP_LENGTH = "stepLength";
    String FIELD_START_TIME = "startTime";
    String FIELD_CLICK_COUNT = "clickCount";
    String FIELD_ORDER_COUNT = "orderCount";
    String FIELD_PAY_COUNT = "payCount";
    String FIELD_CATEGORY_ID = "categoryid";

    /**
     * Hbase相关配置
     */
    String HBASE_ROOTDIR = "hbase.rootdir";
    String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT="hbase.zookeeper.property.clientPort";

    String HBASE_TABLE_GPS_TENMINUTE="hbase.table.gps.tenminute";
    String HBASE_TABLE_GPS_HOUR="hbase.table.gps.hour";
    String HBASE_TABLE_GPS_DAY="hbase.table.gps.day";
    String HBASE_COLUMN_CLUSTER="hbase.column.cluster";
    String HBASE_COLUMN_GPSTOTALNUM="hbase.column.gpsTotalNum";
    String HBASE_COLUMN_GPSFREQUENT="hbase.column.gpsFrequent";
    String HBASE_COLUMN_GPSONLINETOTAL="hbase.column.gpsOnlineTotal";
    String HBASE_COLUMN_GPSWEAKNUM="hbase.column.gpsWeakNum";
    String HBASE_COLUMN_GPSNOTOPENNUM="hbase.column.gpsNotOpenNum";
    String HBASE_COLUMN_ZEROSPEEDNUM="hbase.column.zeroSpeedNum";
    String HBASE_COLUMN_GPSOFFLINETOTAL="hbase.column.gpsOfflineTotal";
    String HBASE_COLUMN_GPSNORMALNUM="hbase.column.gpsNormalNum";
    String HBASE_COLUMN_GPSONLINEPERCENT="hbase.column.gpsonlinePercent";
    String HBASE_COLUMN_GPSLOGCONNECTNUM="hbase.column.gpsLogConnectNum";
    String HBASE_COLUMN_GPSLOGCLOSENUM="hbase.column.gpsLogCloseNum";
    String HBASE_COLUMN_GPSLOGCONNECTIONDURATION="hbase.column.gpsLogConnectionDuration";

}

