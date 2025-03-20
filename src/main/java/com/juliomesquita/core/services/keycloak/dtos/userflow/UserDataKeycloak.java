package com.juliomesquita.core.services.keycloak.dtos.userflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDataKeycloak(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("enabled") Boolean enabled
) {
}
