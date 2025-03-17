package com.juliomesquita.core.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KeycloakUserDto(
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("enabled") Boolean enabled,
        @JsonProperty("emailVerified") Boolean emailVerified,
        List<CredentialsData> credentials) {

   public static KeycloakUserDto from(
           String username,
           String email,
           String firstName,
           String password
   ) {
      return new KeycloakUserDto(
              username,
              email,
              firstName,
              true,
              true,
              password != null ? List.of(new CredentialsData(
                      "password",
                      password,
                      false
              )) : null
      );
   }

}

record CredentialsData(
        @JsonProperty("type") String type,
        @JsonProperty("value") String value,
        @JsonProperty("temporary") Boolean temporary) {

}
