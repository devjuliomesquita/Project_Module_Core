package com.juliomesquita.core.services.keycloak.dtos;

public record KeycloakTest(
        String client_id,
        String client_secret,
        String grant_type
) {
   public static KeycloakTest with(
           String client_id,
           String client_secret
   ) {
      return new KeycloakTest(
              client_id,
              client_secret,
              "client_credentials"
      );
   }
}
