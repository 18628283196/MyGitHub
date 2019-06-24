package com.itheima.day01.array

import scala.collection.mutable.ArrayBuffer

object ArrayDemo {
  def main(args: Array[String]): Unit = {
    //定义一个可变长度为10，类型为Int的固定大小数组
    val array = new Array[Int](10)
    array(1) = 10
    array(2) = 20
    println(array(1))
    println("============================")

    //直接使用apply方法进行生成一个数组
    val array2 = Array(1,2,3)
    //访问数组的元素
    println(array2(2))
    println("============================")

    //我们也可以通过ArrayBuffer来定义一个变长数组
    val array3 = new ArrayBuffer[Int]()
    //array3.append(20)
    println(array3.mkString(","))
    val array4 = new ArrayBuffer[String]()
    array4.append("helloword")
    //println(array4.mkString(","))
    println("=============================")

    //我们可以通过Array的ofDim方法来定义一个多维的数组，多少行，多少列，都是我们自己定义说了算
    val dim = Array.ofDim[Double](3,4)
    dim (1)(1) = 11.11
    //println(dim.mkString(","))
    //对数组进行遍历
    for (x <- dim){
      println(x.mkString(","))
    }
    println("=============================")

    //数组的常见算法
    val array6 = ArrayBuffer(1,2,3,4,5,6)
    println(array6.sum)
    println(array6.max)
    println(array6.sorted)
    println(array6.sorted.reverse)
    println("=================================")



  }

}

