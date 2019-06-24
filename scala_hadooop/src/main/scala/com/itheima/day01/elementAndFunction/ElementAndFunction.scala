package com.itheima.day01.elementAndFunction

import scala.collection.mutable

object ElementAndFunction {
  def main(args: Array[String]): Unit = {
    val listFunc = List("name","age","zhangsan","lisi")
    println(listFunc.map(x=>x+"hello"))
    println(listFunc.map(_.toUpperCase()))
    println("======================================")

    //flatmap：flat即压扁，压平，扁平化，效果就是将集合中的每个元素的子元素映射到某个函数并返回新的集合
    val listFunc2 = List("address","phone")
    println(listFunc2.flatMap(x=>x+"WORLD"))
    println("======================================")

    val queue1=new mutable.Queue[Int]()
    queue1 += 1
    queue1 ++= List(2,3,4)
    println(queue1)
    println("===================================")

    //按照进入队列顺序，删除队列当中的元素（弹出队列）
    val dequeue = queue1.dequeue()
    println(dequeue)
    println(queue1)
    println("======================================")

    //向队列当中加入元素（入队列操作）
    queue1.enqueue(5,6,7)
    println(queue1)
    println("====================================")

    //获取第一个与最后一个元素
    println(queue1.head)
    println(queue1.last)
    println("================================")

    //将二元函数引用集合当中的函数
    val reduceList = List(1,2,3,4,5)
    val reduceRightList = reduceList.reduceRight(_ - _)
    val reduceLeftList = reduceList.reduceLeft(_ - _)
    val reduceLeftList2 = reduceList.reduceLeft((x,y)=>x-y)
    val reduceRightList2 = reduceList.reduceRight((x,y)=>x-y)
    println(reduceRightList)
    println(reduceRightList2)
    println(reduceLeftList)
    println(reduceLeftList2)
    println("=================================")

    /**
      * 2、折叠、化简folder操作
    fold函数将上一步返回的值作为函数的第一个参数继续传递参与运算，
    直到list中的所有元素被遍历。可以把reduceLeft看做简化版的foldLeft。相关函数：fold，foldLeft，foldRight，可以参考reduce的相关方法理解。
    reduce的本质其实就是fold操作，只不过我们使用fold操作的时候，需要指定初始值
    fold操作
      */
    val foldList = List(1,9,2,8)
    val foldResult = foldList.fold(10)((x,y)=>x+y)
    println(foldResult)
    println("===================================")

    ////50-1-9-2-8 = 30
    val foldLeftResult = foldList.foldLeft(50)((x,y)=>x-y)
    println(foldLeftResult)
    //2+(1-(9-(2-8)))
    val foldRightResult = foldList.foldRight(2)((x,y)=>x-y)
    println(foldRightResult)
    println("=========================")

    //拉链操作
    //对于多个List集合，我们可以使用Zip操作，将多个集合当中的值绑定到一起去
    val zipList1 = List("name","age","sex")
    val zipList2 = List("zhangsan",28)
    val zip = zipList1.zip(zipList2)
    println(zip)
    val toMap1 = zip.toMap
    println(toMap1)
    println("=====================")

    //对于集合当中的元素，我们也可以使用迭代器来进行遍历
    val listIterator =List(1,2,"zhangsan")
    val iterator = listIterator.iterator
    while (iterator.hasNext){
      println(iterator.next())
    }





  }

}
