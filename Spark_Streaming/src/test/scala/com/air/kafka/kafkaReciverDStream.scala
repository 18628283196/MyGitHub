package com.air.kafka

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 使用reciver消费kafka的数据
  */
object KafkaReciverDStream {
  def main(args: Array[String]): Unit = {
    //创建sparkconf
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    //创建sparkstreaming
    val ssc = new StreamingContext(conf, Seconds(2))

    //指定topic
    val topic = "luatest"
    val topicMap = topic.split(",").map((_, 1)).toMap

    //消费数据
    val lines = KafkaUtils.createStream(ssc, "node01:2181,node02:2181,node03:2181", "test001", topicMap)

    //打印消息
    lines.foreachRDD(rdd=>rdd.foreach(println(_))+"\t"+"============")

    //启动streamingcontxt
    ssc.start()
    ssc.awaitTermination()
  }
}
