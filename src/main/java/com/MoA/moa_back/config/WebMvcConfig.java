package com.MoA.moa_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      
        registry.addResourceHandler("/images/board/**")
        .addResourceLocations("file:///D:/upload/board-images/");

        registry.addResourceHandler("/images/daily/**")
              .addResourceLocations("file:///D:/upload/daily-images/");

        registry.addResourceHandler("/images/used-trade/**")
              .addResourceLocations("file:///D:/upload/used-trade-images/");

        registry.addResourceHandler("/profile/file/**")
                .addResourceLocations("file:///D:/upload/profile/");
    }
}
