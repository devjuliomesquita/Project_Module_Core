package com.juliomesquita.core.services;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
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
      TokenClientKeycloak tokenClient = this.keycloakFacade.getLoginFlow().getTokenClient();
      System.out.println(tokenClient);
      TokenUserKeycloak tokenUserKeycloak = this.keycloakFacade.getLoginFlow().loginUser("amandaMara", "password");
      System.out.println(tokenUserKeycloak);
   }
}
