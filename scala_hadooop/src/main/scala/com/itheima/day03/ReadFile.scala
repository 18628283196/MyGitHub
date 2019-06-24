package com.itheima.day03

import scala.io.{BufferedSource, Source}

object ReadFile {
  def main(args: Array[String]): Unit = {
    val file: BufferedSource = Source.fromFile("F:\\娱乐头条\\input\\wordcount.txt","GBK")
    val lines: Iterator[String] = file.getLines()
    for(line<-lines){
      println(line)
    }
    file.close()

    val source = Source.fromURL("http://www.baidu.com")
    val string:String=source.mkString
    println(string)
    source.close()
  }
}
