package com.juliomesquita.core.services;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApplication implements CommandLineRunner {

   private final KeycloakFacade keycloakFacade;

   public StartApplication(KeycloakFacade keycloakFacade) {
      this.keycloakFacade = keycloakFacade;
   }

   @Override
   public void run(String... args) throws Exception {
      this.keycloakFacade.getUserFlow().findUsers();
      this.keycloakFacade.getUserFlow().findUsers();
      this.keycloakFacade.getUserFlow().findUsers();
      this.keycloakFacade.getUserFlow().findUsers();
   }
}
