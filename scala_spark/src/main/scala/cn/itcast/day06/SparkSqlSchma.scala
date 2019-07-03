package cn.itcast.day06


import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SparkSqlSchma {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName(this.getClass.getSimpleName).master("local[*]").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    //设置日志级别
    sparkContext.setLogLevel("WARN")
    //读取文件内容，通过文件构建RDD
    val fileRDD: RDD[String] = sparkContext.textFile("F:\\data.txt")
    //文本内容的每一行进行切分
    val arryRDD: RDD[Array[String]] = fileRDD.map(x=>x.split(" "))
    //将每一个数组里面的三个值取出来，构建ROW对象
    val rowRDD: RDD[Row] = arryRDD.map(x=>Row(x(0).toInt,x(1),x(2).toInt))
    //构建structType对象
    val struct: StructType = (new StructType).add("id",IntegerType,true,"id第一个字段").add("name",StringType,true,"name第二个字段").add("age",IntegerType,true,"age第三个字段")
    //通过row以及structType来得到dataframe
    val personDataFrame: DataFrame = sparkSession.createDataFrame(rowRDD,struct)
    //打印schema信息
    personDataFrame.printSchema()
    //创建视图,普通视图（创建或替换），会随着Session会话的消失而消失
    personDataFrame.createOrReplaceTempView("t_person")
    //全局视图可以跨会话访问
    personDataFrame.createOrReplaceGlobalTempView("t_person2")



    sparkSession.sql("select * from t_person").show()
    //当我们创建新的会话的时候普通视图就访问不到，要访问全局视图需要在视图名前加global_temp.
    sparkSession.newSession().sql("select * from global_temp.t_person2").show()

    //释放资源
    sparkContext.stop()
    sparkSession.close()

  }
}
