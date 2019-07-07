package cn.itcast.spider.rulecompute.launch

import java.util.regex.Pattern

import cn.itcast.spider.common.util.decode.MD5

/**
  * 数据脱敏工具类
  */
object EncrpedData {


  def excryedPhone(httpCookie: String): String = {
    /**
      * 实现思路
      * 1.定义一个md5的加密类
      * 2.定义手机号码的正则表达式
      * 3.匹配手机号
      * 4.拿到手机号的前一位和后一位进行判断
      * 5.判断手机号码的前一位是否是数字，然后判断手机号的后一位
      *
      */

    //定义一个MD5加密类
    val md5 = new MD5
    //用局部变量接收一下cookie，进行替换返回值
    var encryed = httpCookie
    //手机号正则
    val phonePattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\\d{8}")
    //匹配规则：java.util.regex.Matcher[pattern=((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\d{8} region=0,1440 lastmatch=]
    val phoneMatcher = phonePattern.matcher(httpCookie)
    //查找手机号
    while (phoneMatcher.find()) {
      //手机号的前一个index，注：phoneMatcher.group()找到的手机号
      val lowIndex = httpCookie.indexOf(phoneMatcher.group()) - 1
      //手机号的后一个index
      val highIndex = lowIndex + phoneMatcher.group().length() + 1
      //手机号的前一个字符
      val lowLetter = httpCookie.charAt(lowIndex).toString()

      //如果前一位字符不是数字，那就要看后一位是否是数字
      if (!lowLetter.matches("^[0-9]$")) {
        //如果字符串的最后是手机号，直接替换即可
        if (highIndex < httpCookie.length()) {
          //手机号的后一个字符
          val highLetter = httpCookie.charAt(highIndex).toString()
          //后一位也不是数字，那说明这个字符串就是一个电话号码
          if (!highLetter.matches("^[0-9]$")) {
            encryed = encryed.replace(phoneMatcher.group(), md5.getMD5ofStr(phoneMatcher.group()))
          }
        } else {
          encryed = encryed.replace(phoneMatcher.group(), md5.getMD5ofStr(phoneMatcher.group()))
        }
      }
    }
    encryed
  }


  def excryedID(httpCookie: String) ={
    /**
      * 实现思路
      * 1.定义一个md5的加密类
      * 2.定义手机号码的正则表达式
      * 3.匹配手机号
      * 4.拿到手机号的前一位和后一位进行判断
      * 5.判断手机号码的前一位是否是数字，然后判断手机号的后一位
      *
      */

    //定义一个MD5加密类
    val md5 = new MD5
    //用局部变量接收一下cookie，进行替换返回值
    var encryed = httpCookie
    //手机号正则
    val phonePattern = Pattern.compile("(\\d{18})|(\\d{17}(\\d|X|x))|(\\d{15})")
    //匹配规则：java.util.regex.Matcher[pattern=((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\d{8} region=0,1440 lastmatch=]
    val phoneMatcher = phonePattern.matcher(httpCookie)
    //查找手机号
    while (phoneMatcher.find()) {
      //手机号的前一个index，注：phoneMatcher.group()找到的手机号
      val lowIndex = httpCookie.indexOf(phoneMatcher.group()) - 1
      //手机号的后一个index
      val highIndex = lowIndex + phoneMatcher.group().length() + 1
      //手机号的前一个字符
      val lowLetter = httpCookie.charAt(lowIndex).toString()

      //如果前一位字符不是数字，那就要看后一位是否是数字
      if (!lowLetter.matches("^[0-9]$")) {
        //如果字符串的最后是手机号，直接替换即可
        if (highIndex < httpCookie.length()) {
          //手机号的后一个字符
          val highLetter = httpCookie.charAt(highIndex).toString()
          //后一位也不是数字，那说明这个字符串就是一个电话号码
          if (!highLetter.matches("^[0-9]$")) {
            encryed = encryed.replace(phoneMatcher.group(), md5.getMD5ofStr(phoneMatcher.group()))
          }
        } else {
          encryed = encryed.replace(phoneMatcher.group(), md5.getMD5ofStr(phoneMatcher.group()))
        }
      }
    }
    encryed
  }



  /*def main(args: Array[String]): Unit = {
    val  cookie = "userId4logCookie=13818791413; useridCookie=13818791413; userCodeCookie=13818791413"
    println(excryedPhone(cookie))
  }*/

}
