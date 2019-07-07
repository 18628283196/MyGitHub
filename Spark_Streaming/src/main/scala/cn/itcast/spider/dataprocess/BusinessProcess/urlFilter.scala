package cn.itcast.spider.dataprocess.BusinessProcess

import cn.itcast.spider.common.bean.AccessLog

import scala.collection.mutable.ArrayBuffer

/**
  * 数据清洗
  */
object urlFilter {
  //过滤掉符合规则的数据
  def filterUrl(accessLog: AccessLog, filterRuleList: ArrayBuffer[String]): Boolean = {

    /**
      * 实现思路：
      * 1.设置一个 变量flag=true
      * 2.循环遍历过滤规则，根据请求的地址进行匹配，匹配上flag=false
      * 3.将flag返回
      */

    var isMatch = true
    filterRuleList.foreach(rule=>{
      if(accessLog.request.matches(rule))
        isMatch = false
    })
        isMatch
  }

}
