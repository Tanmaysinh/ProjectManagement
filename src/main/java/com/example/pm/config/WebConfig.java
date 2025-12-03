package com.example.pm.config;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ActiveUserArgumentResolver activeUserArgumentResolver;

    public WebConfig(ActiveUserArgumentResolver activeUserArgumentResolver) {
        this.activeUserArgumentResolver = activeUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(activeUserArgumentResolver);
    }
}