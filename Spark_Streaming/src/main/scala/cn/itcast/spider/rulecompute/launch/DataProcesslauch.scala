package cn.itcast.spider.rulecompute.launch

import cn.itcast.spider.common.bean.AccessLog
import cn.itcast.spider.common.util.jedis.{JedisConnectionUtil, PropertiesUtil}
import cn.itcast.spider.dataprocess.BusinessProcess._
import kafka.serializer.StringDecoder
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * 这是我们的数据预处理的驱动类
  * 实现:链路统计，数据清洗，脱敏，拆分，封装，解析，历史爬虫判断，结构化，数据推送等需求
  */
object DataProcesslauch {



  //这个方法是业务的驱动方法，不实现具体业务逻辑(优雅停止)
  def main(args: Array[String]): Unit = {
    //当应用程序停止的时候，会将当前数据的批次处理完成后才停止
    System.setProperty("spark.streaming.stopGracefullyOnShutdown","true")
    //创建sparconf对象
    val conf: SparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]").
      set("spark.metrics.conf.executor.source.jvm.class", "org.apache.spark.metrics.source.JvmSource")  //监控级别
    //创建sparkcontext对象
    val sc = new SparkContext(conf)

    //读取kafka集群地址
    val brokers: String = PropertiesUtil.getStringByKey("default.brokers","kafkaConfig.properties")
    //本以为是两个参数，其实是实现一个功能，查看源代码后发现broker.list是旧版本命令,现在直接可以使用新版本命令bootstrap
    val kafkaParams: Map[String, String] = Map(
      "metadata.broker.list"->brokers
    )
    //指定消费的topic
    val topic: Set[String] = Set(PropertiesUtil.getStringByKey("source.nginx.topic","kafkaConfig.properties"))

    //这个方法中实现我们的具体业务逻辑
    val ssc = setupSsc(sc,kafkaParams,topic)

    ssc.start()
    ssc.awaitTermination()

  }

  /**
    * 具体业务逻辑
    * 实现:链路统计，数据清洗，脱敏，拆分，封装，解析，历史爬虫判断，结构化，数据推送等需求
    * @param sc
    * @param kafkaParams
    * @param topic
    * @return
    */
  def setupSsc(sc: SparkContext, kafkaParams: Map[String, String], topic: Set[String]) = {
    val ssc = new StreamingContext(sc,Seconds(2))
    //从kafka中消费数据
    //(String, String)：参数1：分区号  参数2：内容
    val lines: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,topic)

    /**
      * 数据清洗思路：
      * 1.读取数据库配置的过滤规则
      * 2.将读取到的数据库过滤规则广播到executor节点
      * 3.监视广播变量是否发生了改变，如果发生改变，则重新广播
      * 4.对rdd中的数据进行 正则表达式的匹配，如果匹配上需要过滤，否则不过滤
      */
      //1.读取数据库配置的过滤规则
    val filterRuleList: ArrayBuffer[String] = AnalyzerRuleDB.queryFilterRule()

      //这个关键字表示线程安全，会被多个线程使用
    @volatile var filterRuleRef: Broadcast[ArrayBuffer[String]] = sc.broadcast(filterRuleList)


      //获取内容
    val data: DStream[String] = lines.map(_._2)
    //打印数据
    //data.foreachRDD(rdd=>rdd.foreach(println(_)))

    //获取jedis连接
    val jedis = JedisConnectionUtil.getJedisCluster
    //业务处理  foreachRDD在driver端执行
    data.foreachRDD(rdd=>{

      //监视过过滤规则是否发生了改变
      val filterRef: Broadcast[ArrayBuffer[String]] = broadcastProcess.monitorFilterRule(sc,filterRuleRef,jedis)



      //todo 1:首先使用缓存将rdd进行存储，如果rdd在我们的job中多次反复使用的话，要加上缓存，提高执行效率
      rdd.cache()

      //todo 2:将kafka消费出来的字符串转换为bean对象，方便后续使用
      val accessLogRDD: RDD[AccessLog] = DataSplit.parseAccesslog(rdd)



      //todo 3:链路统计
      val serverDStream: Unit = BusinessProcess.linkCount(accessLogRDD,jedis)
      //打印IP地址
     //serverDStream.foreach(println(_))

      //todo 4:数据清洗
      val filterRDD: RDD[AccessLog] = accessLogRDD.filter(accessLog=>urlFilter.filterUrl(accessLog,filterRef.value))

      //输出测试
      filterRDD.foreach(println(_))

      //TODO 5：数据脱敏操作（将手机号、身份证号进行md5算子加密）
      filterRDD.map(record=>{
        record.httpCookie= EncrpedData.excryedPhone(record.httpCookie)
        record.httpCookie = EncrpedData.excryedID(record.httpCookie)

      })

      //TODO：释放资源
      rdd.unpersist()


    })

    ssc
  }
}


