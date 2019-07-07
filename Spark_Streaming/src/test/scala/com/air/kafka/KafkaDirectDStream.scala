package com.air.kafka

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 通过direct消费kafka
  */
object KafkaDirectDStream {
  def main(args: Array[String]): Unit = {
    //创建sparkconf
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    val ssc: StreamingContext = new StreamingContext(conf,Seconds(2))

    val topics = Set("luatest")
    val kafkaParams = Map(
      "metadata.broker.list"->"node01:9092,node02:9092,node03:9092"
    )
    //设置回滚点
    ssc.checkpoint("./ckpt")
    val lines: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,topics)
    //打印数据
    lines.foreachRDD(rdd=>rdd.foreach(println(_)))

    ssc.start()
    ssc.awaitTermination()
  }

}
