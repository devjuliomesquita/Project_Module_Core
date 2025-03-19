package com.juliomesquita.core.controllers.manageusers.dtos;

import java.util.List;

public record ListRolesInfoResponse(
        List<RoleInfoResponse> rolesInfos
) {
}
