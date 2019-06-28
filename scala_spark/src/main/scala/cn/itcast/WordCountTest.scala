package cn.itcast

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountTest {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf().setAppName("wordcount").setMaster("local[2]")
    val sparkContext = new SparkContext(config)
    sparkContext.setLogLevel("WARN")

    val datalin: RDD[String] = sparkContext.textFile("F:\\娱乐头条\\input\\wordcount.txt")

    val words: RDD[String] = datalin.flatMap(x=>x.split(" "))

    val wordcount: RDD[(String, Int)] = words.map(x=>(x,1))

    val sorted = wordcount.sortBy(x=>x._2)

    val count: RDD[(String, Int)] = sorted.reduceByKey((x, y)=>(x+y))
    val word: Array[(String, Int)] = count.collect()
    println(word.toMap)
  }
}
