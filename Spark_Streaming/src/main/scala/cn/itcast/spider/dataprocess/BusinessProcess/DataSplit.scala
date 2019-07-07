package cn.itcast.spider.dataprocess.BusinessProcess

import java.util.regex.Pattern

import cn.itcast.spider.common.bean.AccessLog
import cn.itcast.spider.common.util.decode.{EscapeToolBox, RequestDecoder}
import cn.itcast.spider.common.util.jedis.PropertiesUtil
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  * time_local .."#CS#".. request .."#CS#".. request_method .."#CS#".. content_type
  * .."#CS#".. request_body .."#CS#".. http_referer .."#CS#".. remote_addr ..
  * "#CS#".. http_user_agent .."#CS#".. time_iso8601 .."#CS#".. server_addr .."#CS#".. http_cookie.."#CS#"..ngx.var.connections_active;

  *
  * 将kafka消费出来的字符串转换为bean对象，方便后续使用
  * 04/Jul/2019:21:29:32 +0800#CS#POST /B2C40/dist/main/css/flight.css HTTP/1.1#CS#POST#CS#application/x-www-form-urlencoded; charset=UTF-8#CS#json=%7B%22depcity%22%3A%22CAN%22%2C+%22arrcity%22%3A%22WUH%22%2C+%22flightdate%22%3A%2220180220%22%2C+%22adultnum%22%3A%221%22%2C+%22childnum%22%3A%220%22%2C+%22infantnum%22%3A%220%22%2C+%22cabinorder%22%3A%220%22%2C+%22airline%22%3A%221%22%2C+%22flytype%22%3A%220%22%2C+%22international%22%3A%220%22%2C+%22action%22%3A%220%22%2C+%22segtype%22%3A%221%22%2C+%22cache%22%3A%220%22%2C+%22preUrl%22%3A%22%22%2C+%22isMember%22%3A%22%22%7D#CS#http://b2c.csair.com/B2C40/modules/bookingnew/main/flightSelectDirect.html#CS#192.168.25.100#CS#Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36#CS#2019-07-04T21:29:32+08:00#CS#192.168.25.111#CS#JSESSIONID=782121159357B98CA6112554CF44321E; sid=b5cc11e02e154ac5b0f3609332f86803; aid=8ae8768760927e280160bb348bef3e12; identifyStatus=N; userType4logCookie=M; userId4logCookie=13818791413; useridCookie=13818791413; userCodeCookie=13818791413; temp_zh=cou%3D0%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-13%3B%E5%B9%BF%E5%B7%9E-%E5%8C%97%E4%BA%AC%3B1%2C0%2C0%3B%26cou%3D1%3Bsegt%3D%E5%8D%95%E7%A8%8B%3Btime%3D2018-01-17%3B%E5%B9%BF%E5%B7%9E-%E6%88%90%E9%83%BD%3B1%2C0%2C0%3B%26; JSESSIONID=782121159357B98CA6112554CF44321E; WT-FPC=id=211.103.142.26-608782688.30635197:lv=1516170718655:ss=1516170709449:fs=1513243317440:pn=2:vn=10; language=zh_CN; WT.al_flight=WT.al_hctype(S)%3AWT.al_adultnum(1)%3AWT.al_childnum(0)%3AWT.al_infantnum(0)%3AWT.al_orgcity1(CAN)%3AWT.al_dstcity1(CTU)%3AWT.al_orgdate1(2018-01-17)
  */
object DataSplit {
  def parseAccesslog(rdd: RDD[String]): RDD[AccessLog] = {
    rdd.map(record=>{
      val fields = record.split("#CS#")

      val Array(timeLocal, request, requestMethod, contentType, requestBody, httpReferer, remoteAddr, httpUserAgent,
      timeIso8601, serverAddr, httpCookie, connectionsActive) = fields

      //提前cookie信息，将里面的kv键值对放入map对象中
      val cookieMap: mutable.Map[String, String] = {

        var tempMap: mutable.Map[String, String] = new mutable.HashMap[String,String]()

        if(!httpCookie.equals("")){
            httpCookie.split(";").foreach(s=>{
              //s就是一个键值对，比如userCodeCookie=13818791413
              val kv = s.split("=")
              if(kv.length>1){
                try {
                  val chPattern = Pattern.compile("u([0-9a-fA-F]{4})")
                  val chMatcher = chPattern.matcher(kv(1))
                  var isUnicode = false
                  while (chMatcher.find()) {
                    isUnicode = true
                  }
                  if (isUnicode) {
                    tempMap += (kv(0) -> EscapeToolBox.unescape(kv(1)))
                  } else {
                    tempMap += (kv(0) -> RequestDecoder.decodePostRequest(kv(1)))
                  }
                } catch {
                  case e: Exception => e.printStackTrace()
                }
              }
            })
        }
        tempMap
      }

      //在cookie中获取jessionId和userId4logcookie
      val cookie_jessionid = PropertiesUtil.getStringByKey("cookie.JSESSIONID.key","cookieConfig.properties")
      val cookie_userid = PropertiesUtil.getStringByKey("cookie.userId.key","cookieConfig.properties")

      val cookieValue_jessionid = cookieMap.getOrElse(cookie_jessionid,"NULL")
      val cookieValeu_userid = cookieMap.getOrElse(cookie_userid,"NULL")


      AccessLog(timeLocal, request, requestMethod, contentType, requestBody, httpReferer, remoteAddr, httpUserAgent,
        timeIso8601, serverAddr, httpCookie, connectionsActive.toInt,cookieValue_jessionid,cookieValeu_userid)
    })

  }

}

