package cn.itcast.day05

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object IpLocation {

  //将IP地址转换为long类型的数据，便于后期二分查找
  def ip2Long(ip:String):Long={
    val split: Array[String] = ip.split("\\.")
    var returnNum:Long = 0
    for (num<-split){
      returnNum=num.toLong | returnNum << 8L
    }
    return returnNum
  }


  //通过二分查找法，将ip转换为long的数字，通过二分查找法，查找ip落在哪一个经纬度范围内
  def binarySearch(longIp: Long, value: Array[(String, String, String, String)]): Int = {
    //定义二分查找的初始下标
    var start = 0;
    //定义二分查找的结束下标
    var end = value.length - 1
    //如果初始下标等于结束下标，查找结束，不等于则一直查找
    while (start <= end) {
      //定义中间下标，通过比较，确定初始与结束值是否与中间下标相等
      var middle = (start + end) / 2
      //如果ip大于初始值，小于结束值，直接返回，注意这里需要用到return
      if (longIp >= value(middle)._1.toLong && longIp <= value(middle)._2.toLong) {
        return middle
      }
      //如果ip小于中间值，ip位于左边
      if (longIp < value(middle)._1.toLong) {
        end = middle - 1
      }
      //如果ip大于中间值，ip位于右边
      if (longIp > value(middle)._2.toLong) {
        start = middle + 1
      }
    }
    //为了防止报错，直接返回一个数字
    -1
  }



  def main(args: Array[String]): Unit = {
    //创建sparkcontext对象
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName).setMaster("local[*]")
    val sc = new SparkContext(conf)

    //读取IP地址库信息
    val cityIps: RDD[(String, String, String, String)] = sc.textFile("F:\\Scala\\day05\\授课资料\\服务器访问日志根据ip地址查找区域\\用户ip上网记录以及ip字典\\ip.txt")
      .map(x => x.split("\\|")).map(x => (x(2), x(3), x(x.length - 2), x(x.length - 1)))
    //读取营运商日志
    val logs: RDD[String] = sc.textFile("F:\\Scala\\day05\\授课资料\\服务器访问日志根据ip地址查找区域\\用户ip上网记录以及ip字典\\20090121000132.394251.http.format").map(x=>x.split("\\|")(1))

    //将要广播的数据收集到driver端进行广播（如果数据了非常大，不适合做广播）
    //广播数据在哪里？diver
    val cityIpslist: Array[(String, String, String, String)] = cityIps.collect()
    val broadcastIpsList: Broadcast[Array[(String, String, String, String)]] = sc.broadcast(cityIpslist)

    //遍历运营商日志数据，获取每一个IP地址将IP地址进行十进制转换
    //mappartition在哪执行的？execute
    val result = logs.mapPartitions(iter=>{
      //获取广播变量中的值
      val broadCastValue: Array[(String, String, String, String)] = broadcastIpsList.value
      //遍历迭代器，获取每一个IP地址
      iter.map(ip=>{
      //将IP地址转换为龙类型的数字
        val ipNum: Long = ip2Long(ip)
        //拿到IP地址去广播变量中匹配，通过二分法查找获取对应的下标
        val index: Int = binarySearch(ipNum,broadCastValue)
        //将查找的结果返回，根据IP地址，返回对应的经纬度信息
        if(index == 1){
          (("",""),0)
        }else{
          ((broadCastValue(index)._3,broadCastValue(index)._4),1)
        }
      })
    })

    //统计相同经纬度出现的次数
    val finalResult: RDD[((String, String), Int)] = result.reduceByKey(_+_)
    finalResult.foreach(println(_))

  }

}
