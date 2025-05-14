package com.MoA.moa_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.board-image-path}")
    private String boardImagePath;

    @Value("${file.daily-image-path}")
    private String dailyImagePath;

    @Value("${file.usedtrade-image-path}")
    private String usedTradeImagePath;

    @Value("${file.profile-path}")
    private String profilePath;

    @Value("${file.message-image-path}")
    private String messageImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/board/**")
                .addResourceLocations("file:" + boardImagePath);

        registry.addResourceHandler("/images/daily/**")
                .addResourceLocations("file:" + dailyImagePath);

        registry.addResourceHandler("/images/used-trade/**")
                .addResourceLocations("file:" + usedTradeImagePath);

        registry.addResourceHandler("/profile/file/**")
                .addResourceLocations("file:" + profilePath);

        registry.addResourceHandler("/images/message/**")
                .addResourceLocations("file:" + messageImagePath);
    }
}

