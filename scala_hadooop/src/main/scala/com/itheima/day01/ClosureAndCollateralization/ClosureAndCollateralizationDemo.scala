package com.itheima.day01.ClosureAndCollateralization

/**
  * 闭包与柯里化
柯里化存在的意义是什么？？？注意：柯里化是scala当中面向函数式编程导致的一种必然的结果，
最终推导而来产生的一种现象
  */
object ClosureAndCollateralizationDemo {

  def main(args: Array[String]): Unit = {
    //柯里化的定义形式
    def kery(x:Int)(y:Int):Int={
      x+y
    }
    println(kery(3)(5))

    //柯里化的推导过程,注意方法的返回值不要定义任何的返回值类型
    val keryResult = (x:Int)=>(y:Int)=>{
      x+y
    }

    def keryMethod(x:Int)={
      (y:Int)=>x+y
    }
    println(keryMethod(20))
    println(keryMethod(20)(10))
    println("=====================")

    //注意，方法当中的函数，调用了方法的参数，就叫做闭包
    val a = Array("hello","world")
    val b = Array("Hello","world")
    println(a.corresponds(b)(_.equalsIgnoreCase(_)))


  }
}
