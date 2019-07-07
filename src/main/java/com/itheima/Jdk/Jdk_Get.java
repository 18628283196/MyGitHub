package com.itheima.Jdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Jdk_Get {
    public static void main(String[] args) throws IOException {
        //演示原生jdk发送get请求

        //确定首页的URL
        String indexUrl = "http://www.itcast.cn";
        //发送请求，获取数据
        //将string类型的字符串转换成URL对象
        URL url = new URL(indexUrl);
        //通过URL获取远程连接
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //设置请求方式 请求参数 请求头
        urlConnection.setRequestMethod("GET");//设置请求方式一定要大写，默认请求方式是get
        //获取数据：元素API操作获取响应体的数据是通过流的形式获取
        InputStream inputStream = urlConnection.getInputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = inputStream.read(b)) != -1){
            System.out.println(new String(b,0,len));
        }
        inputStream.close();
    }
    //自己再次练习过的代码
    public void test() throws IOException {
        String url = "www.itcast.com";
        URL url1 = new URL(url);
        HttpURLConnection connetion = (HttpURLConnection) url1.openConnection();
        connetion.setRequestMethod("get");
        InputStream inputStream = connetion.getInputStream();
        int len;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read())!= -1){
            System.out.println(new String(bytes,0,len));
        }


    }
}
