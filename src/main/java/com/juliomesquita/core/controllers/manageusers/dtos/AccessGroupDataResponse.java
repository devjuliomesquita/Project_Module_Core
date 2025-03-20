package com.juliomesquita.core.controllers.manageusers.dtos;

public record AccessGroupDataResponse(
        Boolean view,
        Boolean viewMembers,
        Boolean manageMembers,
        Boolean manage,
        Boolean manageMembership
) {
}
