package com.MoA.moa_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        String basePath = os.contains("win")
            ? "file:///D:/upload/"
            : "file:/Users/seo._b2/upload/";

        registry.addResourceHandler("/images/board/**")
                .addResourceLocations(basePath + "board-images/");

        registry.addResourceHandler("/images/daily/**")
                .addResourceLocations(basePath + "daily-images/");

        registry.addResourceHandler("/images/used-trade/**")
              .addResourceLocations("file:///D:/upload/used-trade-images/");

        registry.addResourceHandler("/profile/file/**")
                .addResourceLocations(basePath + "profile/");

        registry.addResourceHandler("/images/message/**")
                .addResourceLocations(basePath + "message/");
    }
}
