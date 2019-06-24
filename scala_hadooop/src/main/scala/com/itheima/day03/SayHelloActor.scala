package com.itheima.day03

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class SayHelloActor extends Actor{
  override def receive: Receive = {
    case "hello"=>println("收到hello，回应hello too")
    case "ok"=>println("收到ok,回应OK too")
    case "exit"=>{
      println("收到exit指令，退出系统")
      context.stop(self)//停止actoref
      context.system.terminate()//退出actorsystem
    }
    case _=>println("匹配不到")
  }
}
object SayHelloActorDemo {
  //创建一个actorsystem，专门用于创建actor
  private val actorFactory = ActorSystem("actorFactory")
  //2. 创建一个Actor的同时，返回Actor的ActorRef
  //   说明
  //(1) Props[SayHelloActor] 创建了一个 SayHelloActor实例，使用反射
  //(2) "sayHelloActor" 给actor取名
  //(3) sayHelloActorRef: ActorRef 就是 Props[SayHelloActor] 的ActorRef
  //(4) 创建的SayHelloActor 实例被ActorSystme接管
  private val sayHelloActorRef : ActorRef=actorFactory.actorOf(Props[SayHelloActor],"sayhelloActor")

  def main(args: Array[String]): Unit = {
    //给SayhelloActor发消息
    sayHelloActorRef ! "hello"
    sayHelloActorRef ! "ok"
    sayHelloActorRef ! "ok~"
    //研究异步如何退出ActorSystem
    sayHelloActorRef ! "exit"

  }
}
