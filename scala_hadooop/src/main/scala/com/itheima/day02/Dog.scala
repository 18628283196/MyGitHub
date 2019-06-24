package com.itheima.day02

class Dog(name:String,age:Int){
  //在scala当中，可以直接将代码写在class当中，而在java当中，
  //代码必须包含在方法当中。
  //其实在scala当中，虽然你把代码写在了Class类当中，经过编译之后，
  //class类的代码都进入到了主构造器方法当中去了
  println(name)
  println(age)

  var gender: String = ""
  def this(name:String,age:Int,gender:String){
    //每个辅助构造器，都必须以其他辅助构造器，或者主构造器的调用作为第一句
    this(name:String,age:Int)
    this.gender = gender
  }
  var color = ""

  /**
    * 我们也可以通过private来进行修饰我们的构造器，
    */
  private def this(name:String,age:Int,color:String,gender:String){
    this(name:String,age:Int)
    this.color = color
  }

}
