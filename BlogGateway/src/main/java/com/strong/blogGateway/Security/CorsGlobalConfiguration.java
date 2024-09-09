package com.strong.blogGateway.Security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsGlobalConfiguration {

    @Value("${Gateway.Cors.Url}")
    private String CORS_URL;

    @Value("${Gateway.Cors.Methods}")
    private String CORS_METHODS;

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        List<String> allowedOrigins = Arrays.asList(CORS_URL.split(","));
        config.setAllowedOrigins(allowedOrigins);
        List<String> allowedMethods = Arrays.asList(CORS_METHODS.split(","));
        config.setAllowedMethods(allowedMethods);
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
