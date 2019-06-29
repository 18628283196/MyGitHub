package cn.itcast.day07

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 实时计算中需要过滤掉测试用户
  * 游戏中分为两类用户：一类是正式用户，一类是测试用户（测试人员）
  * 那么这个时候，测试用户的消费是不能计算到公司的收入，也就是说需要过滤掉这部分的人员
  */
object TransformUserList {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    val sc: StreamingContext = new StreamingContext(conf,Seconds(5))

    //首先需要一份测试数据名单
    val testUserList: Array[(String, Boolean)] = Array(("zhangsan",true),("lisi",true))
    //装换为RDD
    val testUserRDD: RDD[(String, Boolean)] = sc.sparkContext.makeRDD(testUserList)

    //读取访问日志
    //20190208 zhangsan
    //20180505 lisi

    //注册一个监听的IP地址和端口，用来收集数据
    val logDstream: ReceiverInputDStream[String] = sc.socketTextStream("node01",9999)
    //需要先对输入的数据做一个转换操作（username，log），便于后面对每个batchrdd与定义好的rdd进行join
    val tulpeDStream: DStream[(String, String)] = logDstream.map(log=>(log.split(" ")(1),log))

    val vailLogDStream: DStream[String] = tulpeDStream.transform(rdd => {
      /**
        * (String, (String, Option[Boolean]))
        * String username
        * String log日志
        * Option[Boolean]：匹配上了就是true，没匹配上就是false
        */
      val joinRDD: RDD[(String, (String, Option[Boolean]))] = rdd.leftOuterJoin(testUserRDD)
      //连接后进行过滤
      val filterDDD: RDD[(String, (String, Option[Boolean]))] = joinRDD.filter(tp => {
        //如果有值是true，那就可以得到这个值true，如果没有就会得到一个默认值，false就是这个默认值
        if (tp._2._2.getOrElse(false)) false
        else true
      })
      filterDDD.map(tp => (tp._2._1))
    })
    vailLogDStream.print()
    sc.start()
    sc.awaitTermination()

    
  }

}
