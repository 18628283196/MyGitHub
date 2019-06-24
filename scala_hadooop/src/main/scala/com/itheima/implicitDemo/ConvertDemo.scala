package com.itheima.implicitDemo

/**
  * 隐式转换案例一（将我们的Double类型的数据自动转换成Int类型）
  */
class ConvertDemo {

  implicit def ConvertDubleToInt(first:Double):Int=first.toInt

  def main(args: Array[String]): Unit = {
    val num:Int = 3.5
  }
}
