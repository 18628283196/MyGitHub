package cn.itcast.day06

import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * 读取文本文件数据，然后写入到mysql数据库表当中去
  *
  */
object Spark2MySql {
  case class Person(id:Int,name:String,age:Int)
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName(this.getClass.getSimpleName).master("local[*]").getOrCreate()
    val sparkContext = sparkSession.sparkContext

    //读取文件
    val fileRdd: RDD[String] = sparkContext.textFile("F:\\data.txt")
    //切割文件
    val wordsRdd: RDD[Array[String]] = fileRdd.map(x=>x.split(" "))
    //转化为person类
    val personRdd: RDD[Person] = wordsRdd.map(x=>Person(x(0).toInt,x(1),x(2).toInt))
    //再转为dataframe
    //导入隐式转换的包
    import sparkSession.implicits._
    val df: DataFrame = personRdd.toDF()
    //创建一个视图
    df.createOrReplaceTempView("v_person")
    sparkSession.sql("select * from v_person").show()
    //写入mysql中
    val properties = new Properties()
    properties.setProperty("user","root")
    properties.setProperty("password","root")
    df.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/webdb","v_person",properties)

    //释放资源
    sparkContext.stop()
    sparkSession.close()


  }
}
