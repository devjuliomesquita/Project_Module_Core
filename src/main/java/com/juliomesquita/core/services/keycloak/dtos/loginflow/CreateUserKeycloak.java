package com.juliomesquita.core.services.keycloak.dtos.loginflow;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateUserKeycloak(
        @JsonProperty("username") String username,
         @JsonProperty("email") String email,
         @JsonProperty("firstName") String firstName,
         @JsonProperty("lastName") String lastName,
         @JsonProperty("enabled") Boolean enabled,
        @JsonProperty("emailVerified") Boolean emailVerified,
        List<CredentialsUserKeycloak> credentials) {
}
