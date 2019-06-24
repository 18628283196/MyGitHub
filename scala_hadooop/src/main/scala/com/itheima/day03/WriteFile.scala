package com.itheima.day03

import java.io.PrintWriter

import scala.io.StdIn

object WriteFile {
  def main(args: Array[String]): Unit = {
    val writer = new PrintWriter("F:\\娱乐头条\\input\\wordcount.txt")
    for(i<-1 to 10){
      writer.println(i)
      writer.flush()
    }
    writer.close()

    println("=============================")

    //控制台交互--老API
    print("请输入内容：")
    val str = Console.readLine()
    println("您刚输入的内容是"+str)
    Thread.sleep(1000)

    //控制台交互--新API
    print("请输入内容(新API)：")
    val str1 = StdIn.readLine()
    print("您刚输入的内容是"+str1)
  }

}
