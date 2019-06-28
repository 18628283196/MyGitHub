package com.itheima.day05

import org.apache.spark.{SparkConf, SparkContext}

object MapPartitionsWithIndexDemo {
  def partitionFun(index: Int, iter: Iterator[Int]): Iterator[Int] = {
    println(s"[${index}]:" + iter.mkString(","))
    iter
  }


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    println(sc.defaultParallelism)
    val arr = sc.parallelize(1 to 5,2)
    println(sc.defaultParallelism)
    arr.mapPartitionsWithIndex(partitionFun).collect()
    sc.stop()

  }
}