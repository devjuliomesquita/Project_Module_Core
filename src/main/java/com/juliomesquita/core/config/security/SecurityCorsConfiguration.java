package com.juliomesquita.core.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityCorsConfiguration {
   @Value("${configs.cors.allowed-origins}")
   private String allowedOrigins;

   @Value("${configs.cors.allowed-methods}")
   private String allowedMethods;

   @Value("${configs.cors.allowed-headers}")
   private String allowedHeaders;

   @Value("${configs.cors.exposed-headers}")
   private String exposedHeaders;

   @Value("${configs.cors.max-age}")
   private Long maxAge;
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
      configuration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
      configuration.setAllowedHeaders(List.of(allowedHeaders.split(",")));
      configuration.setExposedHeaders(List.of(exposedHeaders.split(",")));
      configuration.setMaxAge(maxAge);
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }

}
