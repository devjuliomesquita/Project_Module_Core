package com.juliomesquita.core.services.keycloak.dtos.loginflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CredentialsUserKeycloak(
        @JsonProperty("type") String type,
        @JsonProperty("value") String value,
        @JsonProperty("temporary") Boolean temporary
) {
}
