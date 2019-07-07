package com.itheima.Jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

public class JsoupParse {
    /**
     *使用原生js的方式解析HTML文档
     */
    @Test
    public void jsoupOfJs() throws IOException {
        //确定首页
        String indexUrl = "http://www.itcast.cn";
        //使用js发送请求，获取数据
        Document document = Jsoup.connect(indexUrl).get();
        //解析数据
        //解析传智播客首页中的课程数据
        Elements divEls = document.getElementsByClass("nav_txt");
        Element divEl = divEls.get(0);

        Elements ulEls = divEl.getElementsByTag("ul");
        Element ulEl = ulEls.get(0);

        Elements liEls = ulEl.getElementsByTag("li");
        for (Element liEl : liEls) {
           /* Elements aEls = liEl.getElementsByTag("a");
            Element aEl = aEls.get(0);
            String text = aEls.text();
            System.out.println(text);*/
            System.out.println(liEl.text());//两种输出方式都可以
        }
    }

    @Test
    public void jsoupOfSelector() throws IOException {
        //确定首页
        String indexUrl = "http://www.itcast.cn";
        //发送请求，获取document对象
        Document document = Jsoup.connect(indexUrl).get();
        //解析数据：选择器的方式
        Elements aEl = document.select("body > div.wrap > div.banner > div.bans > div.sub_nav > div.nav_txt > ul > li");
        //System.out.println(aEl);
        for (Element element : aEl) {
            //System.out.println(element.select("a").attr("href"));//获取的网址
            System.out.println(element.text());//获取的内容
        }


    }
}
