package com.juliomesquita.core.services.keycloak.dtos.roleflow;

public record RoleDataKeycloak(
        String id,
        String name,
        String description,
        Boolean composite,
        Boolean clientRole,
        String containerId
) {
}

