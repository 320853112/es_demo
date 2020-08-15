package com.xu.utils;

import com.xu.domain.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {

    public  List parseJD(String keyword) throws IOException {
        //获取请求 https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        // parse web page
        Document document = Jsoup.parse(new URL(url), 30000);
        Element jGoodsList = document.getElementById("J_goodsList");
        Elements li = jGoodsList.getElementsByTag("li");
        ArrayList<Content> contents = new ArrayList();
        for (Element element : li) {
            Content currContent = new Content();
            String img = element.getElementsByTag("img").eq(0).attr("src");
            String price = element.getElementsByClass("p-price").eq(0).text();
            String title = element.getElementsByClass("p-name").eq(0).text();
            currContent.setImg(img);
            currContent.setPrice(price);
            currContent.setTitle(title);
            contents.add(currContent);
        }
        return contents;
    }

    public static void main(String[] args) throws IOException {
        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
    }
}
