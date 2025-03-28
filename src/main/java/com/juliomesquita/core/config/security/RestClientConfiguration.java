package com.juliomesquita.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

   @Bean
   public RestClient restClient() {
      return RestClient.builder().build();
   }
}
