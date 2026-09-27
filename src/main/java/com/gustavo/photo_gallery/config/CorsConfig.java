package com.gustavo.photo_gallery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://2026-photo-gallery-frontend.vercel.app"
                )
                .allowedMethods(
                        "GET",
                        "POST",
                        "DELETE",
                        "PUT",
                        "OPTIONS"
                )
                .allowedHeaders("*");
    }
}