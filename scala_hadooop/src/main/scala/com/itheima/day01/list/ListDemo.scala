package com.itheima.day01.list

import scala.collection.mutable.ListBuffer

object ListDemo {
  def main(args: Array[String]): Unit = {
    //注意：列表当中的元素类型可以是不同的，这一点与我们元组类似，但是列表当中的元素是可以删减的
    val list1 = List("hello",20,5.0f)
    println(list1)
    println("===========================")

    //访问列表中元素
    println(list1(0))
    println("============================")

    //列表中添加元素,尾部追加
    val list2 = list1:+50
    //头部追加
    val list3 = 100+:list1
    println(list2)
    println(list3)
    println("============================")

    //Nil是一个空的List，定义为List[Nothing]
    //尾部添加了Nil，那么就会出现List集合里面装List集合的现象
    val list4 = 1::2::3::list1::Nil
    val list4_1 = 1::2::3::list1::Nil::list2
    val list4_2 = 1::2::3::list1::Nil::list2::Nil
    println(list4)
    println(list4_1)
    println(list4_2)
    //获取单个元素
    println(list4.head)
    println(list4.tail.head)
    println(list4.tail.tail.head)
    //插入操作
    //在第二个位置插入一个元素
    val list4_3 = list4.head::(22::list4.tail)
    println(list4_3)
    println("=================================")


    //变长list的创建与使用
    val list6 = new ListBuffer[String]
    list6.append("hello")
    list6.append("world")
    //获取到的是每个元素
    println(list6.mkString(","))
    //获取的是一个集合
    println(list6.toList)
    println("=============================")

    val list = List(List(1, 2, 3), List("adfa", "asdfa", "asdf"))
    for(i<-list;from=i;y<-i){
      //println(list)
      println(y)
    }
    println("============================")




  }
}
