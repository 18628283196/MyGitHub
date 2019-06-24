package com.itheima.implicitDemo

class A{
  def readBook(): Unit ={
    println("A说：好好读书。。。")
  }
}

class B{
  def readBook(): Unit ={
    println("B说：看不懂")
  }
  def writerBook(): Unit ={
    println("B说：不会写")
  }
}

class C

object AB{
  implicit def C2B(C:C)=new B()
  //创建一个类的2个类的隐式转换
  //implicit def C2A(c:C)=new A()
}


object B {
  def main(args: Array[String]): Unit = {
    //导包
    //1. import AB._ 会将AB类下的所有隐式转换导进来
    //2. import AB._C2A 只导入C类到A类的的隐式转换方法
    //3. import AB._C2B 只导入C类到B类的的隐式转换方法

    import AB._
    val c = new C
    c.writerBook()
    c.readBook()



  }
}
