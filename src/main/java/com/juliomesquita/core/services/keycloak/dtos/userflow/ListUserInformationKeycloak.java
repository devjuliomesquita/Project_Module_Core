package com.juliomesquita.core.services.keycloak.dtos.userflow;

import java.util.List;

public record ListUserInformationKeycloak(
        List<UserInformationKeycloak> body
) {
}
