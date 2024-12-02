package com.avirantEnterprises.information_collector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/images/**", "/css/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/Users/Saurav/Downloads/information-collector/information-collector/upload-dir/")
                .setCachePeriod(3600)  // optional cache duration
                .resourceChain(true);
    }
}


