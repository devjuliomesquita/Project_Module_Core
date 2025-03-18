package com.juliomesquita.core.services;

import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
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
//      TokenClientKeycloak tokenClient = this.keycloakFacade.getLoginFlow().getTokenClient();
//      System.out.println(tokenClient);
//      TokenUserKeycloak tokenUserKeycloak = this.keycloakFacade.getLoginFlow().loginUser("amandaMara", "password");
//      System.out.println(tokenUserKeycloak);
//      this.keycloakFacade.getLoginFlow().createUser(new CreateUserKeycloak(
//              "eygon",
//              "Eygon@gmail.com",
//              "Eygon",
//              "Cardoso",
//              true,
//              false,
//              List.of(CredentialsUserKeycloak.create())
//      ));

//      TokenUserKeycloak tokenUserKeycloakRefresh = this.keycloakFacade
//              .getLoginFlow()
//              .refreshTokenUser(tokenUserKeycloak.refresh_token());
//      System.out.println(tokenUserKeycloakRefresh);
//
//      this.keycloakFacade.getLoginFlow().resetPassword(
//              "6143be76-0627-4b44-b1e3-de66666ea455", CredentialsUserKeycloak.create());

      TokenUserKeycloak tokenUserKeycloak2 = this.keycloakFacade.getLoginFlow().loginUser("amandaMara", "teste");
      System.out.println(tokenUserKeycloak2);
//
//      this.keycloakFacade.getLoginFlow().logoutUser(tokenUserKeycloak.refresh_token());
   }
}
