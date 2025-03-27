package com.juliomesquita.core.services.email.implementations;

import org.thymeleaf.context.Context;

import java.util.Map;

public class CreateContext {
   private CreateContext() {
   }

   public static Context create(final Map<String, Object> variables) {
      final Context context = new Context();
      context.setVariables(variables);
      return context;
   }
}
