package cn.itcast.day07

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamUpdateStateByKey {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象
    val conf: SparkConf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    val sc: StreamingContext = new StreamingContext(conf,Seconds(5))

    /**
      * 需要设置checkpoint目录，缓存历史批次数据
      * metadata checkpoint：将定义的streaming计算信息保存到容错存储里面（hdfs），有利于运行streaming应用程序的driver端进行恢复
      * datacheckpoint：将生成的rdd保存到hdfs上
      */
    sc.checkpoint("./ckpt")
    //读取文件
    val words: ReceiverInputDStream[String] = sc.socketTextStream("node01",9999)

    val wordcounts = words.flatMap(_.split(" ")).map((_,1)).updateStateByKey(updateFunc)
    wordcounts.print()
    sc.start()
    sc.awaitTermination()

    }
  /**
    * 这里需要两个参数
    * newvalue：当前批次的某个单词出现的值的序列集合（Hadoop=>Seq[Int](1,1,1,1)）
    * runningCount:历史批次跑出来的某个单词数量的总值（hadoop=>Option[Int](10)）
    * @param newvalue
    * @param runningCount
    *Option有两个子类别，Some和None。当程序回传Some的时候，代表这个函式成功地给了你一个String，
    *   而你可以透过get()函数拿到那个String，如果程序返回的是None，则代表没有字符串可以给你
    */
  def updateFunc(newvalue:Seq[Int],runningCount:Option[Int]): Option[Int] ={
    val newCount = newvalue.sum+runningCount.getOrElse(0)
    Option(newCount)
  }

}
