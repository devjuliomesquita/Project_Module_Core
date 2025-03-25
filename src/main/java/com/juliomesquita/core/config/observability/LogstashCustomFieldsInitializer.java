package com.juliomesquita.core.config.observability;

import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LogstashCustomFieldsInitializer implements ApplicationListener<ApplicationReadyEvent> {
   @Value("${spring.application.name}")
   private String appName;

   @Override
   public void onApplicationEvent(ApplicationReadyEvent event) {
      LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
      context.getLogger("ROOT")
              .info(
                      "Application Started",
                      StructuredArguments.keyValue("app_name", appName)
              );
   }
}
