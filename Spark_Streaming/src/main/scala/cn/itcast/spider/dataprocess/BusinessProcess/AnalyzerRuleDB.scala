package cn.itcast.spider.dataprocess.BusinessProcess

import cn.itcast.spider.common.util.database.QueryDB

import scala.collection.mutable.ArrayBuffer

/**
  * 数据库的操作类
  */
object AnalyzerRuleDB {
  /**
    * 查询过滤规则
    * @return
    */
  def queryFilterRule() = {
    //编写SQL语句
    val sql = "select value from nh_filter_rule where type=0 "

    //指定要查询的字段
    val filed = "value"

    //查询规则信息并返回
    val list: ArrayBuffer[String] = QueryDB.queryData(sql,filed)

    list
  }

}
