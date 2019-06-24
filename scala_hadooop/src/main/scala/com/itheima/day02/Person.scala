package com.itheima.day02

class Person {
  //定义一个属性，叫做name的，使用val不可变量来进行修饰
  // 用val修饰的变量是可读属性，有getter但没有setter（相当与Java中用final修饰的变量）
  val name:String="zhangsan"

  //定义一个属性，叫做age的，使用var可变量来进行修饰
  //用var修饰的变量都既有getter，又有setter
  var age:Int=28

  //类私有字段，只能在类的内部使用或者伴生对象中访问
  private val address:String="流浪地球"

  //类私有字段，访问权限更加严格的，该字段在当前类中被访问
  //在伴生对象里面也不可以访问
  private[this] var pet="小强"

  //在类中定义了一个方法
  def hello(first:Int,second:String):Int={
    println(first+"\t"+second)
    250
  }

  /**
    * 定义了一个函数
    */
  val func1=(x:Int,y:Int)=>{
    x+y
  }


}
