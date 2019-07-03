package cn.itcast.day06

import org.apache.spark.sql.SparkSession

object SparkHiveSql {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName(this.getClass.getSimpleName).master("local[*]")
      .enableHiveSupport() //开启hive的支持，可以让我们无缝的使用hive的SQL语法操作数据
      .getOrCreate()

    val sparkContext = sparkSession.sparkContext
    //创建一张hive表
    sparkSession.sql("create table if not exists student(id int,name string,age int) row format delimited fields terminated by ','")
    //加载Student.csv
    sparkSession.sql("load data local inpath'./data/student.csv' overwrite into table student")
    //查询年龄大于20的人
    sparkSession.sql("select * from student where age>20").show()

    sparkContext.stop()
    sparkSession.close()

  }
}
