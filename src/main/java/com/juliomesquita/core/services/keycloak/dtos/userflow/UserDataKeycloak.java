package com.juliomesquita.core.services.keycloak.dtos.userflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDataKeycloak(
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("enabled") Boolean enabled
) {
}
