package com.itheima.day01.gaojieFunction

object GaoJieFuction {
  def main(args: Array[String]): Unit = {
    val myFunc = (x:Int)=>{
      x*x
    }

    val myArray = Array(1,3,5,7).map(myFunc)
    println(myArray.mkString(","))
    println("==========================")

    //没有名字的函数即是匿名函数，我们可以通过函数表达式来设置匿名函数
    println((x:Int,y:String)=>x+y)
    println("==========================")

    //高阶函数
    //1、能够接受函数作为参数的方法，叫做高阶函数
    val func3:(Int,String)=>(String,Int)={
      (x,y)=>(y,x)
    }

    def myMethod3(hello:(Int,String)=>(String,Int)):Int={
      val resultFunc = hello(20,"hello")
      resultFunc._2

    }
    println(myMethod3(func3))
    println("==========================")

    //2、高阶函数同样可以返回一个函数类型
    def myFunc4(x:Int)=(y:String)=>y
    println(myFunc4(50))
    println("=============================")

    val array = Array(1,2,3,4,5,6,7,8,9)
    ////map当中需要传入一个函数，我们可以直接定义一个函数
    val array1 = array.map((x:Int)=>x*2)
    println(array1.mkString(","))
    //进一步化简 参数推断省去类型信息
    array.map((x)=>x*2)
    //进一步化简
    array.map(x=>x*2)
    //进一步化简  如果变量只在=>右边只出现一次，可以用_来代替
    val array2 = array.map(2*_)
    println(array2.mkString(","))

  }

}
