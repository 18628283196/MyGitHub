package com.itheima.day01.function

/**
  * 函数定义的两种形式
第一种形式：
val  函数名 = (参数名1：参数类型1，参数名2：参数类型2)  =>  {函数体}

第二种形式：
val  函数名 ：（参数类型1，参数类型2） => (返回类型) = {
	函数体
}
  */
object FunctionDemo {
  def main(args: Array[String]): Unit = {
    //定义一个标准函数
    val func1 = (x:Int,y:Int)=>{
      x * y
      x+y
    }
    println(func1(1,5))
    println("==============================")

    val func2:Int => Int ={
      x => x * x
    }
    println(func2(3))
    println("===============================")

    //示例四：定义一个函数，参数值是两个，分别是Int和String，返回值是一个元组，分别是String和Int
    val func3:(Int,String)=>(String,Int)={
      (x,y) => (y,x)
    }
    println(func3(10,"zhangsan"))
    println("================================")

    //我们可以定义一个函数，然后再定义一个方法，但是方法的参数是一个函数
    val myfunc = (x:Int)=>{
      x*x
    }
    val myfunc1:(Int) => Int={
      x=>x*x
    }
    def methodFunction(f:Int=>Int):Int={
      println(100)
      f(100)
    }
    println(methodFunction(myfunc))
    println(methodFunction(myfunc1))
    println("=====================================")

    //方法可以自动转换成函数作为参数传递到方法里面去
    def method2(x:Int)={x*x}
    def methodFunc2(x:Int=>Int):Int={
      x(100)
    }
    println(methodFunc2(method2))
    println("====================================")

    //我们可以通过 _  将我们的一个方法，转换成函数
    def method3(x:Int,y:String)={
      println(x)
      x
    }
    println(method3 _)
    println("====================================")

    //当val被声明为lazy时，他的初始化将被推迟，直到我们首次对此取值，适用于初始化开销较大的场景。
    def init():String={
      println("init方法执行")
      "嘿嘿嘿，喵喵喵~"
    }
    lazy val msg = init()
    println("lazy方法没有执行")
    println(msg)

  }
}
