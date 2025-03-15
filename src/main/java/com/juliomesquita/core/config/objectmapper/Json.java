package com.juliomesquita.core.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;

public enum Json {
   INSTANCE;

   public static ObjectMapper mapper() {
      return INSTANCE.mapper.copy();
   }

   public static <T> T readValue(
           final String json, final Class<T> clazz
   ) {
      return invoke(() -> INSTANCE.mapper.readValue(
              json,
              clazz
      ));
   }

   public static String writeValueAsString(final Object o) {
      return invoke(() -> INSTANCE.mapper.writeValueAsString(o));
   }

   private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
           .dateFormat(new StdDateFormat())
           .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
           .modules(
                   new JavaTimeModule(),
                   new Jdk8Module(),
                   this.afterburnerModule()
           )
           .featuresToDisable(
                   DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                   DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                   DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                   SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
           )
           .build();

   private AfterburnerModule afterburnerModule() {
      AfterburnerModule afterburnerModule = new AfterburnerModule();
      afterburnerModule.setUseValueClassLoader(false);
      return afterburnerModule;
   }

   private static <T> T invoke(final Callable<T> callable) {
      try {
         return callable.call();
      } catch (final Exception e) {
         throw new RuntimeException();
      }
   }
}
