package com.example.jsoupnewscrawling.service;

import entity.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    private static String NEWS_URL = "https://www.hkbs.co.kr/news/articleList.html?sc_section_code=S1N1&view_type=sm";

    public List<News> getNewsData() {
        List<News> newsList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(NEWS_URL).get();
            Elements contents = document.select("section ul.type2 li");

            for (Element content : contents) {
                News news = News.builder()
                        .image(content.select("a img").attr("abs:src"))
                        .subject(content.select("h4 a").text())
                        .url(content.select("a").attr("abs:href"))
                        .build();
                newsList.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리 방식은 변경 가능
        }
        return newsList;
    }
}
