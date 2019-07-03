package cn.itcast.day06

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //2.定义配置文件对象
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("wordcount")

    //如果在集群上运行，需要改一下conf配置
    //val conf = new SparkConf().setAppName("wordCount")

    //1.获取sparkContext对象，   用于操作我们的数据
    val sparkContext = new SparkContext(sparkConf)
    //3.设置日志级别，避免打印大多日志
    sparkContext.setLogLevel("WARN")
    //4.读取本地文件
    val file:RDD[String] = sparkContext.textFile("F:\\娱乐头条\\input\\wordcount.txt")

    //在集群上连接运行需要改读取位置
    //val file = sparkContext.textFile("")

    //5.每一行进行拆分
    val flatMap:RDD[String] = file.flatMap(x=>x.split(" "))
    //6.拆分出来的单词记为1
    val map:RDD[(String,Int)] = flatMap.map(x=>(x,1))
    //7.按照我们的单词进行划分，相同的单词划分到一起
    val key:RDD[(String,Int)] = map.reduceByKey((x,y)=>x+y)
    //val result:RDD[(String,Int)] = key.map(x=>(x._1,x._2))
    //8.按照单词出现的次数进行排序，false表示降序，默认升序
    val by:RDD[(String,Int)] = key.sortBy(x=>x._2,false)
    //9.调用collect收集我们的结果，然后通过print打印出来
    val collect:Array[(String,Int)] = by.collect()
    println(collect.toBuffer)
    //将我们最终结果保存到文件里面去
    key.saveAsTextFile("F:\\娱乐头条\\input\\wordcountoutput.txt")

    //集群运行需要改上面的内容
    //key.saveAsTextFile(args(1))

    //关闭sparkcontext
    sparkContext.stop()
  }

}
