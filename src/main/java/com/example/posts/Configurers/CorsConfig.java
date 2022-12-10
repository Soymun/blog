package com.example.posts.Configurers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE","PUT","OPTIONS")
                .allowedHeaders("*")
                .allowedOrigins("Access-Control-Allow-Origin")
                .allowCredentials(true)
                .maxAge(180);
    }
}
