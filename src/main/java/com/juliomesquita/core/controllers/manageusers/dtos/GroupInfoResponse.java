package com.juliomesquita.core.controllers.manageusers.dtos;

public record GroupInfoResponse(
        String id,
        String name,
        String path,
        AccessGroupDataResponse access
) {
}
