package com.MoA.moa_back.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class NewsService {

    // 카테고리별로 매핑된 네이버 뉴스 URL
    private static final Map<String, String> CATEGORY_URL_MAP = Map.of(
        "politics", "https://news.naver.com/section/100",
        "economy", "https://news.naver.com/section/101",
        "society", "https://news.naver.com/section/102",
        "culture", "https://news.naver.com/section/103",
        "world", "https://news.naver.com/section/104",
        "it", "https://news.naver.com/section/105",
        "ranking", "https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111"
    );

    public List<Map<String, String>> fetchNewsByCategory(String category) {
        String targetUrl = CATEGORY_URL_MAP.getOrDefault(category, CATEGORY_URL_MAP.get("ranking"));
        System.out.println("카테고리 요청: " + category);

        try {
            Document doc = Jsoup.connect(targetUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements newsItems = "ranking".equals(category)
                    ? doc.select("a[href^='https://n.news.naver.com/article/'], a[href^='https://n.news.naver.com/mnews/article/']")
                    : doc.select("a[href*='n.news.naver.com'][href*='/article/']");

            List<Callable<Map<String, String>>> tasks = new ArrayList<>();
            Set<String> seenLinks = new HashSet<>();

            for (Element item : newsItems) {
                String link = item.absUrl("href");
                String title = item.text().trim();

                if (seenLinks.contains(link) || link.contains("/comment/")) continue;
                seenLinks.add(link);

                tasks.add(() -> {
                    String finalTitle = title;
                    String uploadTime = "알 수 없음";
                    String thumbnail = "";

                    try {
                        boolean needsCorrection = finalTitle.equals("동영상뉴스")
                                || finalTitle.equals("동영상기사")
                                || finalTitle.length() < 4;

                        Document articleDoc = Jsoup.connect(link)
                                .userAgent("Mozilla/5.0")
                                .timeout(5000)
                                .get();

                        if (needsCorrection) {
                            Element metaTitle = articleDoc.selectFirst("meta[property=og:title]");
                            if (metaTitle != null) {
                                String t = metaTitle.attr("content").trim();
                                if (!t.isEmpty()) finalTitle = t;
                            }
                        }

                        Element imageMeta = articleDoc.selectFirst("meta[property=og:image]");
                        if (imageMeta != null) {
                            thumbnail = imageMeta.attr("content").trim();
                        }

                        Element spanTime = articleDoc.selectFirst("span.media_end_head_info_datestamp_time");
                        if (spanTime != null) {
                            String rawTime = spanTime.hasAttr("data-modify-date-time")
                                    ? spanTime.attr("data-modify-date-time").trim()
                                    : spanTime.attr("data-date-time").trim();
                            if (!rawTime.isEmpty()) {
                                uploadTime = rawTime.replace(" ", "T");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("기사 요청 실패: " + link);
                    }

                    Map<String, String> news = new HashMap<>();
                    news.put("title", finalTitle);
                    news.put("summary", "요약 gpt 고민");
                    news.put("link", link);
                    news.put("uploadTime", uploadTime);
                    news.put("thumbnail", thumbnail);
                    return news;
                });

                if (tasks.size() >= 5) break;
            }

            ExecutorService executor = Executors.newFixedThreadPool(7);
            List<Future<Map<String, String>>> futures = executor.invokeAll(tasks);
            executor.shutdown();

            List<Map<String, String>> result = new ArrayList<>();
            for (Future<Map<String, String>> future : futures) {
                try {
                    result.add(future.get());
                } catch (Exception e) {
                    System.out.println("태스크 처리 실패");
                }
            }

            System.out.println("📦 최종 수집된 뉴스 수: " + result.size());
            Collections.shuffle(result);
            return result.stream().limit(5).toList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}