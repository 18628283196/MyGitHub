package com.itheima.day01.method

/**
  *
scala定义方法的标准格式为
def 方法名(参数名1: 参数类型1, 参数名2: 参数类型2) : 返回类型 = {方法体}
  */
object MethodDemo {
  def main(args: Array[String]): Unit = {
    def hello(first:String,second:Int): Int ={
      second
    }
    println(hello("zhangsan",20))
    println("=================================")

    def hello2(first:Int,second:String): Any ={
      println("hello")
      //20
      second
    }
    println(hello2(10,"aaa"))
    println("=================================")

    //定义一个方法，不定义返回值，可以通过自动推断，返回不同类型的值
    def hello3(first:Int,second:String)={
      if (first > 30){
        first
      }else{
        second
      }
    }
    println(hello3(20,"lisi"))
    println("==================================")

    //示例四：定义一个方法，参数给定默认值，如果不传入参数，就使用默认值来代替
    def hello4(first:Int = 10,second:String): Unit ={
      println(first+"\t"+second)
    }
    hello4(second = "helloword")
    println("=================================")

    //示例五：变长参数，方法的参数个数不定的，类似于java当中的方法的...可变参数
    def hello5(first:Int*)={
      var result = 0;
      //对里面的数进行循环
      for(ags <- first){
        result += ags
      }
      println(result)
    }
    hello5(10)
    hello5(10,20,30)
    println("================================")

    //示例六：递归函数。我们可以定义一个方法，使得方法自己调用自己，
    // 形成一个递归函数，但是方法的返回值类型必须显示的手动指定
    def hello6(first:Int): Int ={
      if(first<=1){
        println("终于循环完了")
        1
      }else{
        println(s"${first}-1="+ (first-1))
        hello6(first-1)
      }
    }
    println(hello6(10))
    println("========================================")

    //示例七：定义一个方法，没有显示的指定返回值，那么我们方法当中定义的等号可以省掉
    //注意：如果省掉了=号，那么这个方法强调的就是一个代码执行的过程
    def hello7(first:Int) ={
      println(first)
      20
    }
    println(hello7(10))



  }

}
