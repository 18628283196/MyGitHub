package com.itheima.day02

object ScalaClass {
  def main(args: Array[String]): Unit = {
    //创建对象两种方式。这里都是使用的无参构造来进行创建对象的
    val person = new Person
    val person1 = new Person
    //注意，我们可以使用对象的属性加上_= 给var修饰的属性进行重新赋值
    //其实就是调用set方法，方法名叫做 age_=
    person.age_=(50)
    //直接调用类的属性，其实就是调用get方法
    println(person.age)
    println(person.hello(50,"helloworld"))
    val func = person.func1(10,20)
    println(func)
  }

}
