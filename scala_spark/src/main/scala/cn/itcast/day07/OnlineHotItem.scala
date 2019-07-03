package cn.itcast.day07

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object OnlineHotItem {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    //设置采样时间
    val sc = new StreamingContext(conf,Seconds(5))
    
    //读取文件
    val stream: ReceiverInputDStream[String] = sc.socketTextStream("node01",9999)
    val wordAndOne: DStream[(String, Int)] = stream.map(x=>x.split(" ")(1)).filter(!_.isEmpty).map((_,1))
    //数据处理
    val hotDstream: DStream[(String, Int)] = wordAndOne.reduceByKeyAndWindow((x:Int, y:Int)=>x+y,Seconds(60),Seconds(20))


    val result=hotDstream.transform(rdd=>{
      val tuples: Array[(String, Int)] = rdd.sortBy(_._2,false).take(3)
      sc.sparkContext.makeRDD(tuples)

    })
    //输出结果
    result.print()
    sc.start()
    sc.awaitTermination()

  }
}
