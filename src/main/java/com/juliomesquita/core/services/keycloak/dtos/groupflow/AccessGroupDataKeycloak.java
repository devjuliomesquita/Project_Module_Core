package com.juliomesquita.core.services.keycloak.dtos.groupflow;

public record AccessGroupDataKeycloak(
        Boolean view,
        Boolean viewMembers,
        Boolean manageMembers,
        Boolean manage,
        Boolean manageMembership
) {

}
