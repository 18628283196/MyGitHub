package cn.itcast.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * UV：
  **
  *unique visitor，独立访问者。00：00——23：59，也即1天内某站点的访问人数（以cookie为依据）。一天内同一个访客的多次访问只记为1个UV
  */
object UVDemo {
  def main(args: Array[String]): Unit = {
 /*   val conf = new SparkConf().setAppName("UV").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val fileData: RDD[String] = sc.textFile("F:\\Scala\\day05\\授课资料\\运营商日志\\access.log")

   val fileSplit: RDD[Array[String]] = fileData.map(x=>x.split(" "))

    val ips = fileSplit.map(x=>x(0))
    println(ips)

    val uvs: RDD[(String, Int)] = ips.distinct().map(x=>("UV",1))

    val uv = uvs.reduceByKey(_+_)

    uv.foreach(println)
*/
    /*uv.saveAsTextFile("F:\\Scala\\day05\\out")

    sc.stop()*/

   //todo:构建SparkConf和 SparkContext
    val sparkConf: SparkConf = new SparkConf().setAppName("UV").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(sparkConf)
    //todo:读取数据
    val file: RDD[String] = sc.textFile("F:\\Scala\\day05\\授课资料\\运营商日志\\access.log")
    //todo:对每一行分隔，获取IP地址
    val ips: RDD[(String)] = file.map(_.split(" ")).map(x=>x(0))
    //todo:对ip地址进行去重，最后输出格式 ("UV",1)
    val uvAndOne: RDD[(String, Int)] = ips.distinct().map(x=>("UV",1))
    //todo:聚合输出
    val totalUV: RDD[(String, Int)] = uvAndOne.reduceByKey(_+_)
    totalUV.foreach(println)
    //todo:数据结果保存

    totalUV.saveAsTextFile("F:\\Scala\\day05\\out")
    sc.stop()




  }

}
