package com.itheima.implicitDemo

object Company{
  // //在object中定义隐式值    注意：同一类型的隐式值只允许出现一次，否则会报错
  implicit val aaa="zhangsan"
  implicit val bbb=100000.00
}

class Boss{
  //注意参数匹配的类型 它需要的是string类型的参数
  def callName(implicit name:String):String={
    name+"is coming"
  }

  //定义一个用implicit修饰的参数
  //注意参数的匹配类型 它需要的是double类型的隐式值
  def getMoney()(implicit money:Double):String={
    "当月月薪"+money
  }
}


object Boss {
  //使用import导入并定义好的隐式值，注意：必须先加载不然会报错
  import Company._
  val boss = new Boss()
  println(boss.callName+boss.getMoney())
}
