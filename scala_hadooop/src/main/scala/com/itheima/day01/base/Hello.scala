package com.itheima.day01.base

import scala.util.control.Breaks

object Hello {
  def main(args: Array[String]): Unit = {
    println("I LOVE SCALA")
    var num: Int = 2
    var num1: Int = 3
    var num2 = num + num1
    println(num2)
    println("-------------------------------------------")

    val a = 5;
    val b:Any = if(a<10)
      println("哥是个传说")
    else
      println("这是个大于等于10的数")
    println("-------------------------------------------")

    var e = 1
    val f = while (e<=10){
      e +=1
    }
    println(e)
   //但是while语句的本身是没有任何返回值类型的  也就是while语句最终的返回结果是Unit类型的()
    println(f)
    println("-------------------------------------------")

    //跳出循环的第一种方式
    var g = 10
    val loop = new Breaks
    loop.breakable{
      val h = while(g <=20){
        g +=1
        if(g == 15){
          loop.break()
        }
      }
      println(h)
    }
    println(g+"=============")

    //跳出循环的第二种方式,用标志
    var aa = 10;
    var flag = true
    while (flag){
      aa += 1
      if (aa == 20){
        flag = false
      }
    }
    println("end")
    println("========================================")

    //for表达式,打印乘法表
    for (i <- 1 to 9){
      for(j <- 1 to i){
        if (i == j){
          println(j+"*"+i+"="+(i*j))
        }else{
         // print(j+"*"+i+"="+(i*j)+" ")
          print(s"${j}*${i}=${i*j} ")
        }
      }
    }
    println("======================================")

    for(i <- 1 to 4;j <- 1 to 5){
      print(s"${i}*${j}=${i*j} ")
    }
    println()
    println("======================================")

    //示例二：使用util实现左右两边分别为前闭后开的访问
    for (i<- 1 until 5;j<-2 until 5){
      print(s"${i}*${j}=${i*j} ")
    }
    println()
    println("======================================")


    //示例四：引入变量
    for(i<- 1 to 3;j = 4 -i){
      print(s"${i}*${j}=${i*j} ")
    }

  }
}
