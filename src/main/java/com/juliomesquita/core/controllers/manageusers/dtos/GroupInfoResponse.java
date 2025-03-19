package com.juliomesquita.core.controllers.manageusers.dtos;

import com.juliomesquita.core.services.keycloak.dtos.groupflow.AccessGroupDataKeycloak;

public record GroupInfoResponse(
        String id,
        String name,
        String path,
        AccessGroupDataKeycloak access
) {
}
