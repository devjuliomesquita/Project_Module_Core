package com.juliomesquita.core.config.observability;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ObservationConfiguration {
   private final ObservationRegistry observationRegistry;

   public ObservationConfiguration(final ObservationRegistry observationRegistry) {
      this.observationRegistry = Objects.requireNonNull(observationRegistry);
   }

   @Bean
   public ObservedAspect observedAspect() {
      return new ObservedAspect(observationRegistry);
   }
}
