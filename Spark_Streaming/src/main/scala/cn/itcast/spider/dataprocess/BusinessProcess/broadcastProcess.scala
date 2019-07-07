package cn.itcast.spider.dataprocess.BusinessProcess

import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast
import redis.clients.jedis.JedisCluster

import scala.collection.mutable.ArrayBuffer

/**
  * 广播变量监视类
  */
object broadcastProcess {
  /**
    * 监视过过滤规则是否发生了改变
    * @param sc
    * @param filterRuleRef
    * @param jedis
    * @return
    */
  def monitorFilterRule(sc: SparkContext, filterRuleRef: Broadcast[ArrayBuffer[String]], jedis: JedisCluster) = {
    /**
      * 思路：
      * 1.获取redis里面的标记
      * 2.判断标记是否发生了改变
      *   2.1重新获取数据库的规则
      *   2.2将当前广播变量的值进行删除
      *   2.3将redis的标记重置
      *   2.4将数据重新的广播
      * 3.如果标记没发生改变
      *   3.1将filterRef直接返回
      *
      */
    //获取redis里面的标记
    val needUpdateFilterRuleList: String = jedis.get("filterRuleChangeFlag")

    //判断标记是否发生了改变
    /**
      * needUpdateFilterRuleList != null  判断redis的key是否存在
      * !needUpdateFilterRuleList.isEmpty  判断redis的value的值不为空
      */
    if(needUpdateFilterRuleList != null && !needUpdateFilterRuleList.isEmpty && needUpdateFilterRuleList.toBoolean){
      //2.1重新获取数据库的过滤规则
      val filterRuleListUpdate = AnalyzerRuleDB.queryFilterRule()
      //2.2将当前广播变量的值进行删除
      filterRuleRef.unpersist()
      //2.3将redis的标记重置
      jedis.set("filterRuleChangeFlag","false")
      // 2.4将数据重新的广播
      sc.broadcast(filterRuleListUpdate)
    }else{
      filterRuleRef
    }

  }


}
