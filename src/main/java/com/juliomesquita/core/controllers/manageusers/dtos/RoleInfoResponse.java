package com.juliomesquita.core.controllers.manageusers.dtos;

public record RoleInfoResponse(
        String id,
        String name,
        String description,
        Boolean composite,
        Boolean clientRole,
        String containerId
) {
}
