package com.itheima.day02

object ClassObject {
  /**
    * 同Java一样，如果要运行一个程序，必须要编写一个包含 main 方法的类；
在 Scala 中，也必须要有一个 main 方法，作为入口；
Scala 中的 main 方法定义为 def main(args: Array[String])，而且必须定义在 object 中；
除了自己实现 main 方法之外，还可以继承 App Trait，然后，将需要写在 main 方法中运行的代码，直接作为 object
  的 constructor 代码即可，而且还可以使用 args 接收传入的参数；
    * @param args
    */
  def main(args: Array[String]) {
    if(args.length > 0){
      println("Hello, " + args(0))
    }else{
      println("Hello World1!")
    }
  }
}
//2.使用继承App Trait ,将需要写在 main 方法中运行的代码
// 直接作为 object 的 constructor 代码即可，
// 而且还可以使用 args 接收传入的参数。

object Main_Demo2 extends App{
  if(args.length > 0){
    println("Hello, " + args(0))
  }else{
    println("Hello World2!")
  }

}
