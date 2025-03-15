package com.juliomesquita.core.config.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
   @Bean
   public ObjectMapper objectMapper() {
      return Json.mapper();
   }
}
