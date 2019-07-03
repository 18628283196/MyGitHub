package cn.itcast.day06

import org.apache.spark.sql.{Column, DataFrame, SparkSession}

object SparkSqlDemo {
  case class Person(id:Int,name:String,age:Int)
  def main(args: Array[String]): Unit = {
    //创建sparkSession对象
    val sparkSession: SparkSession = SparkSession.builder().appName(getClass.getSimpleName).master("local[*]").getOrCreate()
    val context = sparkSession.sparkContext
    context.setLogLevel("WARN")

    val fileData = context.textFile("F:\\data.txt")

    val wordsData = fileData.map(x=>x.split(" "))

    val personRDD = wordsData.map(x=>Person(x(0).toInt,x(1),x(2).toInt))

    //导入隐式转换的类，我们的rdd才可以转换成DataFrame
    import sparkSession.implicits._
    //将我们的rdd转换为dataFarame
    val personDF: DataFrame = personRDD.toDF()

    /*********************************sparkSql DSL风格语法 开始**************************************/
    //personDF.printSchema()
    //personDF.show
    //personDF.show(2)
    println("==========================================")
    //查询name字段的所有值
    personDF.select("name").show()
    personDF.select($"name").show()
    personDF.select(personDF.col("name")).show()
    personDF.select(new Column("name")).show()

    //将年龄进行加1
    personDF.select($"id",$"name",$"age"+1).show()
    //按照年龄进行分组
    personDF.groupBy($"age").count().show()
    /*********************************sparkSql sql风格语法 开始**************************************/
    //这方法已经过时了
    val personTable = personDF.registerTempTable("t_person")
    val personView: Unit = personDF.createTempView("t_person_view")
    sparkSession.sql("select * from t_person").show()
    sparkSession.sql("select * from t_person_view").show()
    sparkSession.sql("select * from t_person_view where name='wangwu'").show()

    context.stop()
    sparkSession.close()

    
  }
}
