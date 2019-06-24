package com.itheima.day02

object wordcount {
  def main(args: Array[String]): Unit = {
    var listData = Array("hadoop","spark","fink","spark","spark")
    //var data = listData.flatMap(lin=>lin.split(","))
    var data1 = listData.map(x=>(x,1))
    val data2 = data1.groupBy(x=>x._1)
    //这里适用于分布式集群中做统计
    var data3 = data2.map(x=>(x._1,x._2.map(x=>x._2).sum))

    //下面两种都一样的适用于单节点统计
    var data4 = data2.map(x=>(x._1,x._2.size))
    var data5 = data2.map(x=>(x._1,x._2.length))

    println(data3)

  }

}
