package com.juliomesquita.core.services;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
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
      this.keycloakFacade.getAssociateFlow().disassociateRoleUser("13d1b871-ea12-47cc-8841-4dc207e575e1",
              List.of(new AssociateRolesKeycloak("7dff72ed-6c65-4aae-84fc-53123f4f5ba2", "1 Nível")));
   }
}
