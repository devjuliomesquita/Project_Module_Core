package com.juliomesquita.core.controllers.manageusers.dtos;

public record UserInfosResponse(
        String id,
        String username,
        String firstName,
        String lastName,
        String email,
        Boolean emailVerified,
        Long createdTimestamp,
        Boolean enabled
) {
}
