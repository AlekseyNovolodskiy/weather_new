package com.example.weather_new.config;

import com.example.weather_new.service.AuthServiceFilter.AuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        log.info("Регистрация AuthFilter...");
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setName("authFilter");

        // Исключаем публичные endpoints из фильтра
        registration.addInitParameter("exclusions",
                "/, /register, /autentificate, /health, /ping, /styles.css");

        log.info("AuthFilter зарегистрирован для всех URL");
        return registration;
    }
}