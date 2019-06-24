package com.itheima

class Cat{
  def miaomiao(name:String): Unit ={
    println(name+"喵喵~~")
  }
}

class Dog{
  def wangwang(name:String): Unit ={
    println(name+"旺旺~~")
  }
}

object CatAndDog {
  implicit def catAndDog(cat:Dog):Cat = {
    new Cat
  }

  def main(args: Array[String]): Unit = {
    var dog = new Dog()
    dog.wangwang("大黄狗：")
    dog.miaomiao("假奶狗学猫叫：")
  }
}
