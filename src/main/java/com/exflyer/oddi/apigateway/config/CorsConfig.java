package com.exflyer.oddi.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedMethod(HttpMethod.POST.name());
    config.addAllowedMethod(HttpMethod.PUT.name());
    config.addAllowedMethod(HttpMethod.GET.name());
    config.addAllowedMethod(HttpMethod.DELETE.name());
    config.addAllowedMethod(HttpMethod.OPTIONS.name());
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.setMaxAge(30000L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
    source.registerCorsConfiguration("/**", config);

    return new CorsWebFilter(source);
  }

}
