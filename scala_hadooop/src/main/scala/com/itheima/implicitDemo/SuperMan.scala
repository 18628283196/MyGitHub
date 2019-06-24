package com.itheima.implicitDemo

class Man(val name1: String)

class SuperMan(val name : String){
    def heat = println(name+"超人打小怪兽")
  }


object SuperMan {
  implicit def man2SuperMan(man:Man) = new SuperMan(man.name1)
  def main(args: Array[String]): Unit = {
    val hero = new Man("hero")
    hero.heat
  }

}
