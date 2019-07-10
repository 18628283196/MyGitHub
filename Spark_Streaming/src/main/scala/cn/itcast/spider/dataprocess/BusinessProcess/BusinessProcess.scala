package cn.itcast.spider.dataprocess.BusinessProcess

import cn.itcast.spider.common.bean.AccessLog
import cn.itcast.spider.common.util.jedis.PropertiesUtil
import org.apache.spark.rdd.RDD
import org.json4s.DefaultFormats
import org.json4s.jackson.Json
import redis.clients.jedis.JedisCluster

/**
  * 链路统计功能实现
  */
object BusinessProcess {
  def linkCount(logRDD: RDD[AccessLog], jeids: JedisCluster)= {

  /**
      * 实现思路：
      * 1.统计各个链路的数据采集量
      * 2.统计各个链路的活跃连接数
    * time_local .."#CS#".. request .."#CS#".. request_method .."#CS#".. content_type .."#CS#".. request_body .."#CS#".. http_referer .."#CS#".. remote_addr .."#CS#".. http_user_agent .."#CS#".. time_iso8601 .."#CS#".. server_addr .."#CS#".. http_cookie;

    * 04/Jul/2019:21:29:32 +0800#CS#POST /B2C40/dist/main/css/flight.css HTTP/1.1#CS#POST#CS#application/x-www-form-urlencoded; charset=UTF-8#CS#json=%7B%22depcity%22%3A%22CAN%22%2C+%22arrcity%22%3A%22WUH%22%2C+%22flightdate%22%3A%2220180220%22%2C+%22adultnum%22%3A%221%22%2C+%22childnum%22%3A%220%22%2C+%22infantnum%22%3A%220%22%2C+%22cabinorder%22%3A%220%22%2C+%22airline%22%3A%221%22%2C+%22flytype%22%3A%220%22%2C+%22international%22%3A%220%22%2C+%22action%22%3A%220%22%2C+%22segtype%22%3A%221%22%2C+%22cache%22%3A%220%22%2C+%22preUrl%22%3A%22%22%2C+%22isMember%22%3A%22%22%7D#CS#http://b2c.csair.com/B2C40/modules/bookingnew/main/flightSelectDirect.html#CS#192.168.25.100#CS#Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36#CS#2019-07-04T21:29:32+08:00#CS#192.168.25.111#CS#JSESSIONID=782121159357B98CA6112554CF44321E; sid=b5cc11e02e154ac5b0f3609332f86803; aid=8ae8768760927e280160bb348bef3e12; identifyStatus=N; userType4logCookie=M; userId4logCookie=13818791413; useridCookie=13818791413; userCodeCookie=13818791413; temp_zh=cou%3D0%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-13%3B%E5%B9%BF%E5%B7%9E-%E5%8C%97%E4%BA%AC%3B1%2C0%2C0%3B%26cou%3D1%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-17%3B%E5%B9%BF%E5%B7%9E-%E6%88%90%E9%83%BD%3B1%2C0%2C0%3B%26; JSESSIONID=782121159357B98CA6112554CF44321E; WT-FPC=id=211.103.142.26-608782688.30635197:lv=1516170718655:ss=1516170709449:fs=1513243317440:pn=2:vn=10; language=zh_CN; WT.al_flight=WT.al_hctype(S)%3AWT.al_adultnum(1)%3AWT.al_childnum(0)%3AWT.al_infantnum(0)%3AWT.al_orgcity1(CAN)%3AWT.al_dstcity1(CTU)%3AWT.al_orgdate1(2018-01-17)
      *04/Jul/2019:21:29:32 +0800#CS#POST /B2C40/dist/main/images/common.png HTTP/1.1#CS#POST#CS#application/x-www-form-urlencoded; charset=UTF-8#CS#json=%7B%22depcity%22%3A%22CAN%22%2C+%22arrcity%22%3A%22WUH%22%2C+%22flightdate%22%3A%2220180220%22%2C+%22adultnum%22%3A%221%22%2C+%22childnum%22%3A%220%22%2C+%22infantnum%22%3A%220%22%2C+%22cabinorder%22%3A%220%22%2C+%22airline%22%3A%221%22%2C+%22flytype%22%3A%220%22%2C+%22international%22%3A%220%22%2C+%22action%22%3A%220%22%2C+%22segtype%22%3A%221%22%2C+%22cache%22%3A%220%22%2C+%22preUrl%22%3A%22%22%2C+%22isMember%22%3A%22%22%7D#CS#http://b2c.csair.com/B2C40/modules/bookingnew/main/flightSelectDirect.html?t=S&c1=CAN&c2=WUH&d1=2018-02-20&at=1&ct=0&it=0#CS#192.168.25.100#CS#Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36#CS#2019-07-04T21:29:32+08:00#CS#192.168.25.111#CS#JSESSIONID=782121159357B98CA6112554CF44321E; sid=b5cc11e02e154ac5b0f3609332f86803; aid=8ae8768760927e280160bb348bef3e12; identifyStatus=N; userType4logCookie=M; userId4logCookie=13818791413; useridCookie=13818791413; userCodeCookie=13818791413; temp_zh=cou%3D0%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-13%3B%E5%B9%BF%E5%B7%9E-%E5%8C%97%E4%BA%AC%3B1%2C0%2C0%3B%26cou%3D1%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-17%3B%E5%B9%BF%E5%B7%9E-%E6%88%90%E9%83%BD%3B1%2C0%2C0%3B%26; JSESSIONID=782121159357B98CA6112554CF44321E; WT-FPC=id=211.103.142.26-608782688.30635197:lv=1516170718655:ss=1516170709449:fs=1513243317440:pn=2:vn=10; language=zh_CN; WT.al_flight=WT.al_hctype(S)%3AWT.al_adultnum(1)%3AWT.al_childnum(0)%3AWT.al_infantnum(0)%3AWT.al_orgcity1(CAN)%3AWT.al_dstcity1(CTU)%3AWT.al_orgdate1(2018-01-17)
      *04/Jul/2019:21:29:32 +0800#CS#POST /B2C40/dist/main/images/loadingimg.jpg HTTP/1.1#CS#POST#CS#application/x-www-form-urlencoded; charset=UTF-8#CS#json=%7B%22depcity%22%3A%22CAN%22%2C+%22arrcity%22%3A%22WUH%22%2C+%22flightdate%22%3A%2220180220%22%2C+%22adultnum%22%3A%221%22%2C+%22childnum%22%3A%
      */
    //1.统计各个链路的数据采集量
    //简写：logs.map(_.split(" ")(9))
    val serverCount: RDD[(String, Int)] = logRDD.map(recored => {
      (recored.serverAddr, 1)
    }).reduceByKey(_ + _)

    //统计各个链路的活跃连接数
    val activeNum: RDD[(String, Int)] = logRDD.map(record => {

      (record.serverAddr, record.connectionsActive)
    }).reduceByKey((x, y) => y)  //x:代表历史累加值   y:代表最新值

    //将数据写入redis集群，rdd现在在哪端？driver
    if(!serverCount.isEmpty() && !activeNum.isEmpty()){
      //将收集好的rdd转换为map
      val serverCountMap: collection.Map[String, Int] = serverCount.collectAsMap()     //也可以serverCount.collect.tomap
      val activeNumMap: collection.Map[String, Int] = activeNum.collectAsMap()

      //将数据写入redis
      //将数据包装到一个map中，进行序列化成字符串
      val fieldMap = Map(
        "serversCountMap"->serverCountMap,
        "activeNumMap"->activeNumMap
      )
      //获取链路统计的key   System.currentTimeMillis().toString:让每次的key不会重复
      val keyName = PropertiesUtil.getStringByKey("cluster.key.monitor.linkProcess","jedisConfig.properties")+System.currentTimeMillis().toString
      //获取链路统计的超时时间
      val expTime = PropertiesUtil.getStringByKey("cluster.exptime.monitor","jedisConfig.properties").toInt
      //将数据写入redis
      val value: String = jeids.setex(keyName,expTime,Json(DefaultFormats).write(fieldMap))



    }
  }

}