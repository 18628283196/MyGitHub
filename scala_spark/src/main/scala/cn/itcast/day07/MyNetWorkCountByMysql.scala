package cn.itcast.day07

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 通过使用mysql累计单词出现的次数
  */
object MyNetWorkCountByMysql {
  Logger.getLogger("org").setLevel(Level.WARN)
  //默认去加载resource目录下的配置文件，application.conf->application.json->application.Properties
  private val config: Config = ConfigFactory.load()
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    //没过5s采集一次数据
    val sc: StreamingContext = new StreamingContext(conf,Seconds(5))
    
    //获取数据
    val words: ReceiverInputDStream[String] = sc.socketTextStream("node01",9999)
    //需要将数据写入mysql，集成sparkSQL，sparkSQL核心是dataframe
    words.foreachRDD(rdd => {

      val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()

      import sparkSession.implicits._

      //创建dataframe
      val wordDataFrame: DataFrame = rdd.map(x => x.split(" ")).toDF("word")

      //将dataframe注册成视图
      wordDataFrame.createOrReplaceTempView("v_words")

      //SQL语句
      val result = sparkSession.sql("select word,count(1) total from v_words group by word")

      val properties = new  Properties()
      properties.setProperty("user",config.getString("db.user"))
      properties.setProperty("password",config.getString("db.password"))
      if(result.rdd.isEmpty()){
        result.write.mode(SaveMode.Append).jdbc(config.getString("db.url"),config.getString("db.table"),properties)
      }
    })
    sc.start()
    sc.awaitTermination()
  }

}
