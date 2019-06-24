package com.itheima.day01.tuple

/**
  * 在scala当中提供元组tuple的数据类型，可以理解tuple为一个容器，可以存放各种不同的数据类型的数据，例如一个Tuple当中既可以存放String类型数据，同时也可以存放Int类型的数据
注意：注意元组一旦创建之后，就是不可变的，也就是说元组当中没有添加和删除元素这一说
  */
object TupleDemo {
  def main(args: Array[String]): Unit = {
    val tuple1 = ("hello",1,5.0f)
    println(tuple1)
    //元组数据的访问
    println(tuple1._1)
    println(tuple1._2)
    println(tuple1._3)
    println("============================")

    //元组的遍历：第一种遍历,迭代器
    println("第一种遍历,迭代器")
    for(x<- tuple1.productIterator){
      println(x)
    }
    println("============================")
    //第二种遍历方式
    println("第二种遍历方式,增强for循环")
    tuple1.productIterator.foreach(x => println(x))


  }

}
