package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

public interface LoginFlowKeycloakService {
   Either<Notification, String> createUser(CreateUserKeycloak data);

   Either<Notification, TokenUserKeycloak> loginUser(String username, String password);

   Either<Notification, TokenUserKeycloak> refreshTokenUser(String refreshToken);

   Either<Notification, Void>  logoutUser(String refreshToken);

   Either<Notification, Void>  resetPassword(String userId, CredentialsUserKeycloak credentials);
}
