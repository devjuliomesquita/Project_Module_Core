package com.juliomesquita.core.services.keycloak.dtos.loginflow;

public record TokenClientKeycloak(
        String access_token,
        Integer expires_in,
        Integer refresh_expires_in,
        String token_type,
        String scope
) {
}
