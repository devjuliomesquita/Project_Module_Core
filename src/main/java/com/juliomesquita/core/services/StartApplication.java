package com.juliomesquita.core.services;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartApplication implements CommandLineRunner {

   private final KeycloakFacade keycloakFacade;

   public StartApplication(KeycloakFacade keycloakFacade) {
      this.keycloakFacade = keycloakFacade;
   }

   @Override
   public void run(String... args) throws Exception {
      List<UserInformationKeycloak> users = this.keycloakFacade.getUserFlow().findUsersByFilter("novoemail@email.com", true);
      System.out.println(users);
   }
}
