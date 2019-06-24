package com.itheima.day03

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.itheima.day03.ActorDemo.bActory

class AActor(bActory:ActorRef) extends Actor{
  override def receive: Receive = {
    case "start"=>{
      println("AActor开始出招,start ok")
      self ! "我打" //发给自己
    }
    case "我打"=>{
      println("AActor(吕小布)厉害，看我佛山无影脚")
      Thread.sleep(1000)
      bActory ! "我打"
    }
  }
}

class BActor extends Actor{
  override def receive: Receive = {
    case "我打"=>println("BActor(乔峰)挺猛，看我降龙十八掌")
    Thread.sleep(1000)
    //通过sender() 可以获取到发现消息的actor的ref,意思是谁发给我的我就回给谁，不用指定
    sender() ! "我打"
  }

}

object ActorDemo{
  ////创建一个actorsystem，专门用于创建actor
  private val actorFactor = ActorSystem("actorFactor")
  //创建bactor的引用/代理
  private val bActory: ActorRef = actorFactor.actorOf(Props[BActor],"actory")
  //创建AActor的引用
  private val aActor: ActorRef = actorFactor.actorOf(Props(new AActor(bActory)))
  def main(args: Array[String]): Unit = {
    aActor ! "start"

  }
}
