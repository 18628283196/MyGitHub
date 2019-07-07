package com.itheima.httpclient;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.IOException;

public class Httpclient_Get {
    public static void main(String[] args) throws IOException {
        /**
         * 模拟httpclient发送get请求
         */
        //确定首页
        String indexUrl ="http://www.itcast.cn";
        /**
         * 注意：如果后期进行一些非常复杂的配置，可以使用这种方式
         * HttpClientBulider builder = new HttpClientBulider(indexUrl);
         * CloseableHttpClient httpclient = builder.build();
         */
        //发送请求  获取数据
        //创建httpclient对象
         CloseableHttpClient httpClient = HttpClients.createDefault();
        //请求方式：请求对象
        HttpGet httpGet = new HttpGet(indexUrl);
        //设置请求头
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        //发送请求，获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取响应行：状态码
        int code = response.getStatusLine().getStatusCode();
        //System.out.println(code);
        if(code == 200){
            //取响应头获
            Header[] headers = response.getHeaders("Content-Type");
            //String value = headers[0].getValue();
            //System.out.println(value);
            HttpEntity entity = response.getEntity();//封装了响应的数据
            //EntityUtils的方法：static：方法类型  返回类型String	方法：toString(HttpEntity entity) 作用：读取实体的内容并将其作为String返回。
            String html = EntityUtils.toString(entity);
            //System.out.println(html);
        }
        response.close();
    }
}
