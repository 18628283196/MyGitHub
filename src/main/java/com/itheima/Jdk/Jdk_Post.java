package com.itheima.Jdk;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Jdk_Post {
    public static void main(String[] args) throws IOException {
        //确定首页URL
        String indexUrl = "http://www.itcast.cn";
        //发送请求，获取数据
        //将string类型的URl转换成URL对象
        URL url = new URL(indexUrl);
        //获取远程连接
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //设置请求参数和请求方式
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);//原生jdk默认关闭了流输出
        //获取流
        InputStream inputStream = urlConnection.getInputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = inputStream.read(b)) != -1){
            System.out.println(new String(b,0,len));
        }
        inputStream.close();
    }

    //再次练习了一次
    public void test() throws IOException {
        String url = "www.itcast.com";
        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        InputStream inputStream = connection.getInputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = inputStream.read())!= -1){
            System.out.println(new String(b,0,len));
        }
    }
}
