package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenClientKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;

public interface LoginFlowKeycloakService {
   TokenClientKeycloak getTokenClient();

   void createUser(CreateUserKeycloak data);

   TokenUserKeycloak loginUser(String username, String password);

   TokenUserKeycloak refreshTokenUser(String refreshToken);

   void logoutUser(String refreshToken);

   void resetPassword(String userId, CredentialsUserKeycloak credentials);
}
