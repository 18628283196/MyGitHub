package com.itheima.day01.map

object MapDemo {
  def main(args: Array[String]): Unit = {
    //不可变映射
    //scala当中的Map集合与java当中的Map类似，也是key，value对形式的
    val map1 = Map("hello" -> "word","name"->"zhangsan","age"->18)

    println("--"+map1.map(x=>x._2))
    println("===========================")


    //可变映射及操作
    val map2 = scala.collection.mutable.Map("hello"->"word","name"->"zhangsan","age"->18)
    map2.+=("address"->"流浪地球")
    println(map2)
    println("===========================")
    //可变map删除元素.注意，删除元素是返回一个删除元素之后的map，原来的map并没有改变
    val map3 = map2.-("address")
    println(map2)
    println(map3)
    println("============================")

    //删除会将原来的map改变
    val map4 = scala.collection.mutable.Map("hello"->"word","name"->"zhangsan","age"->18)
    val map5 = map4.-=("hello")
    println(map4)
    println(map5)
    println("=============================")

    //或者使用覆盖key的方式来更新元素
    println(map2)
    map2 +=("address"->"北京")
    println(map2)
    println("==============================")
    //或者使用 +=来进行更新元素
    //注意，map当中没有phonNo这个key，则不能更细
    map2 +=("address"->"上海","phonNo"->"111")
    println(map2)
    map2 +=("address"->"北京","age"->20)
    println(map2)
    //过key来进行取值
    println(map2.get("name"))
    //通过key来进行取值，如果没有这个key，就用后面给定的默认值
    println(map2.getOrElse("address","非洲"))
    ////通过key来进行取值，真的没有这个key，那么就用后面给定的默认值
    println(map2.getOrElse("phon","119"))
    println(map2)
    println("===================================")

    for ((k,v)<-map2){
      println(k)
      println(v)
    }
    println("=================================")
    //遍历获取所有key
    for (k<-map2.keys){
      println(k)
    }
    println("=================================")
    //遍历获取所有value
    for(v<-map2.values){
      println(v)
    }
    //打印key-value对
    for(x<-map2){
      println(x)
    }
    //将对偶数组转换为map
    val arrayMap = Array(("name","zhangsan"),("age",188))
    val toMap = arrayMap.toMap
    println(toMap)


  }

}
