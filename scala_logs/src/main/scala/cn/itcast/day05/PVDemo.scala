package cn.itcast.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * PV：

page view，浏览量。页面的浏览次数，衡量网站用户访问的网页数量，用户每打开/刷新一次页面就记录一次，多次打开会累计。

UV：

unique visitor，独立访问者。00：00——23：59，也即1天内某站点的访问人数（以cookie为依据）。一天内同一个访客的多次访问只记为1个UV

IP：

独立ip地址，指1天内使用不同ip地址的用户访问网站的数量，同一IP无论访问几个页面，独立IP都只记为1。

说明：一个访客（使用一个账号）使用多个设备（网络）访问会算为多个IP，同一个IP使用不同账号会算为多个UV。

IP侧重于反应网络地址的差异，UV侧重于反应访问者的差异。


---------------------
作者：nomasp
来源：CSDN
原文：https://blog.csdn.net/NoMasp/article/details/45458499
版权声明：本文为博主原创文章，转载请附上博文链接！
  */
object PVDemo {
  def main(args: Array[String]): Unit = {
    //todo：创建sparkconf，设置appName
    //todo:setMaster("local[2]")在本地模拟spark运行 这里的数字表示 使用2个线程
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[2]")
    //todo:创建SparkContext
    val context = new SparkContext(conf)
    //只显示错误信息
    context.setLogLevel("WARN")
    //todo:读取数据
    val textFile: RDD[String] = context.textFile("F:\\Scala\\day05\\授课资料\\运营商日志\\access.log")
    //todo:将一行数据作为输入，输出("pv",1)
    val datas: RDD[(String, Int)] = textFile.map(x=>("PV",1))
    //todo:聚合输出
    val totalPV = datas.reduceByKey(_+_)

    totalPV.foreach(println(_))


    context.stop()
    
  }
}
