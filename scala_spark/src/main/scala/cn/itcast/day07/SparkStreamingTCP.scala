package cn.itcast.day07

import org.apache.spark.sql.catalyst.expressions.Second
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 开发sparkStreaming程序，统计单词出现的次数
  */
object SparkStreamingTCP {
  def main(args: Array[String]): Unit = {
    //构建sparkContext参数
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    val sparkContext = new SparkContext(conf)
    //设置日志输出级别
    sparkContext.setLogLevel("WARN")
    //构建streamingContext对象，每个批处理的时间间隔
    val streamingContext: StreamingContext = new StreamingContext(sparkContext,Seconds(5))
    //注册一个监听的IP地址和端口，用来收集数据
    val lins: ReceiverInputDStream[String] = streamingContext.socketTextStream("node01",9999)
    //切分每一行数据
    val wordsRDD: DStream[String] = lins.flatMap(_.split(" "))
    //每个单词记为一
    val wordAndOne: DStream[(String, Int)] = wordsRDD.map((_,1))
    //分组聚合
    val result: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)
    //打印数据
    result.print()
    //开始执行计算
    streamingContext.start()
    //等待计算完成，主程序阻塞，不能死掉
    streamingContext.awaitTermination()




  }
}
