package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;

public interface LoginClientKeycloakService {
   TokenClientKeycloak getTokenClient();
}
