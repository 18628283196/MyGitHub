package com.itheima.Jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class JsopDocument {
    public static void main(String[] args) throws IOException {
        /**
         * 通过jsoup获取document的方式（意思是通过jsoup来获取dom对象）
         */
        //确定首页
        String indexUrl = "http://www.itcast.cn";
        //发送请求方式，获取数据
        //解析数据：给我一个HTML代码字符串
        String html ="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>获取document的方式一</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //获取dom的方式
        //parse可以解析完整的页面
        Document document = Jsoup.parse(html);
        String title = document.title();
        System.out.println(title);

        //获取document的方式二:最简单的方式通过Jsoup访问数据，而我们一般是用Httpclient模拟浏览器访问
        Document document1 = Jsoup.connect(indexUrl).get();
       // System.out.println(document1);

        //获取document的方式三：获取本地的HTML文件，转换document对象
       // Document document2 = Jsoup.parse(new File(""), "utf-8");

        //获取document的方式四：指定一个HTML的片段，获取dom对象
        String html1 = "<a>Hello World</a>";
        Document document3 = Jsoup.parseBodyFragment(html1);
        //System.out.println(document3.text());

        Document document4 = Jsoup.parse(html1);
        System.out.println(document4.text());
    }
}
