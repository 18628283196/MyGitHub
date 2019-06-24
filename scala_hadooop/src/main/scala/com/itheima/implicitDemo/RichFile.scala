
package com.itheima.implicitDemo

import java.io.File

import scala.io.Source

/**
  * 隐式转换案例二（让File类具备RichFile类中的read方法）
  */

object MyPredef{
//定义隐式转换方法
implicit def file2RichFile(file: File)=new RichFile(file)
}
class RichFile(val f:File) {
  def read()=Source.fromFile(f,"utf-8").mkString
}
object RichFile{
  def main(args: Array[String]) {
    val f=new File("F:\\娱乐头条\\input\\wordcount.txt")
    //使用import导入隐式转换方法
    import MyPredef._
    //通过隐式转换，让File类具备了RichFile类中的方法
    val content=f.read()
    println(content)
  }
}
