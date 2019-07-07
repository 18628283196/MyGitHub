package com.itheima.spider;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class QidianSpider {
    public static void main(String[] args) throws IOException {
        //1.确定首页
        String indexUrl = "https://b.faloo.com/f/579533.html";
        //2.发送请求，获取数据
        //2.1创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.2设置请求方式
        HttpGet httpGet = new HttpGet(indexUrl);
        //设置请求参数和请求头
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        httpGet.setHeader("Referer","https://b.faloo.com/p/579533/12.html");
        //2.4发送请求，获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //2.5获取数据
        String html = EntityUtils.toString(response.getEntity(), "UTF-8");
        //System.out.println(html);
        //3.解析数据
        Document document = Jsoup.parse(html);
        //解析章节数据
        Elements elements = document.select("body > div.ni_1 > div.ni_2 > div > div.ni_3_1 > div.ni_9 > div.ni_0 > div:nth-child(5) > div.ni_16 > div.ni_17 > a ");
        String href = elements.get(0).attr("href");
        String bookUrl = "https:"+href;
        //System.out.println(bookUrl);
        //得到书籍的URL，继续发送请求
        httpClient = HttpClients.createDefault();//覆盖上面的连接对象
        //设定请求方式
        HttpGet httpGet1 = new HttpGet(bookUrl);
        //发送请求，获取响应
        CloseableHttpResponse response1 = httpClient.execute(httpGet1);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        httpGet.setHeader("Referer","https://b.faloo.com/f/579533.html");

        //获取状态码
        int code = response1.getStatusLine().getStatusCode();
        System.out.println(code);
        if(code == 200){
            String h = EntityUtils.toString(response1.getEntity());
            //System.out.println(h);
            Document document1 = Jsoup.parse(h);
            Elements elements2 = document1.select("body > div:nth-child(4) > div.mainbody > div.centent > ul");
            for (Element element : elements2) {
                Elements a = element.select("li > a ");
                System.out.println(a);
                for (Element element1 : a) {
                    String bookHref = element1.attr("href");
                    String bkHref = "https:" + bookHref;
                    System.out.println(bkHref);
                    //设定请求方式
                    HttpGet httpGet2 = new HttpGet(bkHref);
                    //发送数据，获取响应
                    CloseableHttpResponse response2 = httpClient.execute(httpGet2);
                    httpGet2.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
                    //获取数据
                    String ss = EntityUtils.toString(response2.getEntity());
                    Document document2 = Jsoup.parse(ss);
                    Elements select = document2.select("#content");
                    System.out.println(select.text());
                }
            }

            }


        }


    }

