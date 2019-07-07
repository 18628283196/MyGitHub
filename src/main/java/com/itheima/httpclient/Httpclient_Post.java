package com.itheima.httpclient;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Httpclient_Post {
    public static void main(String[] args) throws IOException {
        /**
         * 模拟Httpclient发送Post请求
         */
        //设定首页
        String indexUrl = "http://www.itcast.cn";
        //发送请求 获取数据
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置请求fagns
        HttpPost httpPost = new HttpPost(indexUrl);
        //设置请求参数
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("username","xiaochaun"));
        list.add(new BasicNameValuePair("password","123"));
        HttpEntity entity = new UrlEncodedFormEntity(list,"utf-8");
        httpPost.setEntity(entity);
        //发送请求，获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //获取数据
        //获取响应行--》获取状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            //获取响应体的数据
            String html = EntityUtils.toString(response.getEntity());
            System.out.println(html);
        }
    }
}
