package com.MoA.moa_back.controller;

import com.MoA.moa_back.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/category")
    public ResponseEntity<List<Map<String, String>>> getNewsByCategory(@RequestParam String type) {
        List<Map<String, String>> newsList = newsService.fetchNewsByCategory(type);
        return ResponseEntity.ok(newsList);
    }
}