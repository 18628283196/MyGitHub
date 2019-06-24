package com.itheima.day03

@SerialVersionUID(1L)
class Person extends Serializable{
  override def toString = name + "," + age

  val name = "Nick"
  val age = 20

}

object PersonMain extends App{
  override def main(args: Array[String]): Unit = {

    import java.io.{FileOutputStream, FileInputStream, ObjectOutputStream, ObjectInputStream}
    val nick = new Person
    val out = new ObjectOutputStream(new FileOutputStream("Nick.obj"))
    out.writeObject(nick)
    out.close()

    val in = new ObjectInputStream(new FileInputStream("Nick.obj"))
    val saveNick = in.readObject()
    in.close()
    println(saveNick)
  }
}
