package cn.itcast.day06

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkTopN {
  def main(args: Array[String]): Unit = {
    //获取sparkSession
    val sparkSession: SparkSession = SparkSession.builder().appName("sparkTopN").master("local[2]").getOrCreate()

    //通过sparkSession得到sparkContext，并设置日志级别
    val sparkContext: SparkContext = sparkSession.sparkContext
    sparkContext.setLogLevel("WARN")
    //读取json格式的数据，得到DataFrame
    val jsonDF: DataFrame = sparkSession.read.json("file:///F:\\scala与spark课件资料教案\\spark课程\\3、spark第三天\\资料\\score.txt")

    val schema: Unit = jsonDF.printSchema()
    //  jsonDF.show()
    //通过DF创建临时表
    jsonDF.createOrReplaceTempView("score")
    //通过sql语句执行查询
    sparkSession.sql("select * from score").show()

    println("//***************  求每个班最高成绩学生的信息  ***************/")
    println("    /*******  开窗函数的表  ********/")
    sparkSession.sql("select name,clazz,score, rank() over(partition by clazz order by score desc) rank from score").show()

    println("    /*******  计算结果的表  *******")
    sparkSession.sql("select * from " +
      "( select name,clazz,score,rank() over(partition by clazz order by score desc) rank from score) " +
      "as t " +
      "where t.rank=1").show()
    println("/**************  求每个班最高成绩学生的信息（groupBY）  ***************/")
    sparkSession.sql("select clazz, max(score) max from score group by clazz").show()
    sparkSession.sql("select a.name, b.clazz, b.max from score a, " +
      "(select clazz, max(score) max from score group by clazz) as b " +
      "where a.score = b.max").show()
    println("rank（）跳跃排序，有两个第二名时后边跟着的是第四名\n" +
      "dense_rank() 连续排序，有两个第二名时仍然跟着第三名\n" +
      "over（）开窗函数：\n" +
      "       在使用聚合函数后，会将多行变成一行，而开窗函数是将一行变成多行；\n" +
      "       并且在使用聚合函数后，如果要显示其他的列必须将列加入到group by中，\n" +
      "       而使用开窗函数后，可以不使用group by，直接将所有信息显示出来。\n" +
      "        开窗函数适用于在每一行的最后一列添加聚合函数的结果。\n" +
      "常用开窗函数：\n" +
      "   1.为每条数据显示聚合信息.(聚合函数() over())\n" +
      "   2.为每条数据提供分组的聚合函数结果(聚合函数() over(partition by 字段) as 别名) \n" +
      "         --按照字段分组，分组后进行计算\n" +
      "   3.与排名函数一起使用(row number() over(order by 字段) as 别名)\n" +
      "常用分析函数：（最常用的应该是1.2.3 的排序）\n" +
      "   1、row_number() over(partition by ... order by ...)\n" +  //这个函数不需要考虑是否并列，哪怕根据条件查询出来的数值相同也会进行连续排名
      "   2、rank() over(partition by ... order by ...)\n" +        //使用这个函数，成绩相同的两名并列，下一位同学空出所占的名次
      "   3、dense_rank() over(partition by ... order by ...)\n" +  //使用这个函数，成绩相同的两名并列，下一位同学不空所占名次
      "   4、count() over(partition by ... order by ...)\n" +
      "   5、max() over(partition by ... order by ...)\n" +
      "   6、min() over(partition by ... order by ...)\n" +
      "   7、sum() over(partition by ... order by ...)\n" +
      "   8、avg() over(partition by ... order by ...)\n" +
      "   9、first_value() over(partition by ... order by ...)\n" +
      "   10、last_value() over(partition by ... order by ...)\n" +
      "   11、lag() over(partition by ... order by ...)\n" +
      "   12、lead() over(partition by ... order by ...)\n" +
      "lag 和lead 可以 获取结果集中，按一定排序所排列的当前行的上下相邻若干offset 的某个行的某个列(不用结果集的自关联）；\n" +
      "lag ，lead 分别是向前，向后；\n" +
      "lag 和lead 有三个参数，第一个参数是列名，第二个参数是偏移的offset，第三个参数是 超出记录窗口时的默认值")
    sparkContext.stop()
    sparkSession.close()
  }
}
