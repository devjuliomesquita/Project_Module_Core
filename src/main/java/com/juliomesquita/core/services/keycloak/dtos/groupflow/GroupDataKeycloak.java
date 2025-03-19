package com.juliomesquita.core.services.keycloak.dtos.groupflow;

public record GroupDataKeycloak(
        String id,
        String name,
        String path,
        AccessGroupDataKeycloak access
) {
}
