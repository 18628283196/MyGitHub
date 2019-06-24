package com.itheima.day01.set

object SetDemo {
  def main(args: Array[String]): Unit = {
    //1、不可变集合的创建
    val set1 = Set("1","2","2","3")
    println(set1.mkString(","))
    println("========================")

    //如果我们引入的集合的包是可变的，那么我们创建的集合就是可变的
    val set2 = scala.collection.mutable.Set(1,2,3)
    set2.add(4)
    println(set2)
    set2 += 5
    println(set2)
    ////使用.这个方法添加元素，会返回一个新的集合
    val set3 = set2.+(6)
    println(set2.mkString(","))
    println(set3.mkString("\001"))
    println("===========================")

    //可变集合删除元素
    set3 -= 1
    println(set3.mkString(","))
    set3.remove(2)
    println(set3.mkString(","))
    println("============================")

    //遍历set集合
    for(x<-set3){
      println(x)
    }
    println("========================")

    println(set3.sum)
    println(set3.max)
    println(set3.min)


  }

}
