package cn.itcast.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 求访问的topN
  */
object TopNDemo {
  def main(args: Array[String]): Unit = {
   /* val sc: SparkContext = new SparkContext(new SparkConf().setAppName("TopN").setMaster("local[*]"))

    val fileData: RDD[String] = sc.textFile("F:\\Scala\\day05\\授课资料\\运营商日志\\access.log")

    val url: RDD[(String, Int)] = fileData.map(x=>x.split(" ")).filter(x=>(x.length>10)).map(x=>(x(10),1))

    val topN = url.reduceByKey(_+_)

    val topN1 = topN.sortBy(x=>x._2,false)

    topN1.foreach(println)

    topN1.saveAsTextFile("F:\\Scala\\day05\\topN")

    sc.stop()*/

    val sparkConf: SparkConf = new SparkConf().setAppName("TopN").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    //读取数据
    val file: RDD[String] = sc.textFile("F:\\Scala\\day05\\授课资料\\运营商日志\\access.log")
    //将一行数据作为输入,输出(来源URL,1)
    val refUrlAndOne: RDD[(String, Int)] = file.map(_.split(" ")).filter(_.length>10).map(x=>(x(10),1))
    //聚合 排序-->降序
    val result: RDD[(String, Int)] = refUrlAndOne.reduceByKey(_+_).sortBy(_._2,false)
    //通过take取topN，这里是取前5名
    val finalResult: Array[(String, Int)] = result.take(5)
    println(finalResult.toBuffer)
    sc.stop()

  }
}
