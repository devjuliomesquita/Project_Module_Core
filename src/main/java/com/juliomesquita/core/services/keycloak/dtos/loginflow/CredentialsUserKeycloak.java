package com.juliomesquita.core.services.keycloak.dtos.loginflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CredentialsUserKeycloak(
        @JsonProperty("type") String type,
        @JsonProperty("value") String value,
        @JsonProperty("temporary") Boolean temporary
) {
   public static CredentialsUserKeycloak create(final String value) {
      return new CredentialsUserKeycloak(
              "password",
              value,
              false
      );
   }
}
