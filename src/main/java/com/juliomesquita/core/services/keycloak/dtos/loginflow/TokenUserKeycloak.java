package com.juliomesquita.core.services.keycloak.dtos.loginflow;

public record TokenUserKeycloak(
        String access_token,
        Integer expires_in,
        Integer refresh_expires_in,
        String refresh_token,
        String token_type,
        String session_state,
        String scope
) {
}
