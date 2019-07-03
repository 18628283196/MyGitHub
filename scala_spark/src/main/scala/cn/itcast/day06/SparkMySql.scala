package cn.itcast.day06

import java.util.Properties

import org.apache.spark.sql.SparkSession


//读取mysql数据库当中的数据
object SparkMySql {
  def main(args: Array[String]): Unit = {
    //创建sparkSession对象
    val sparkSession = SparkSession.builder().appName(this.getClass.getSimpleName).master("local[*]").getOrCreate()
    val sparkContext = sparkSession.sparkContext

    val url = "jdbc:mysql://localhost:3306/webdb"
    val tableName = "account"
    val properties = new Properties()
    properties.setProperty("user","root")
    properties.setProperty("password","root")

    sparkSession.read.jdbc(url,tableName,properties).show()

    sparkSession.close()
    sparkContext.stop()

  }
}
