package com.juliomesquita.core.controllers.manageusers.dtos;

public record UserLoginResponse(
        String accessToken,
        Integer expiresIn,
        Integer refreshExpiresIn,
        String refreshToken,
        String tokenType,
        String sessionState,
        String scope
) {
}
