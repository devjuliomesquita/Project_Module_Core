package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerTokenFlow;
import com.juliomesquita.core.controllers.manageusers.dtos.LoginUserRequest;
import com.juliomesquita.core.controllers.manageusers.dtos.RefreshTokenRequest;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/manager-user")
public class ManagerTokenController implements ManagerTokenFlow {
   private static final Logger log = LoggerFactory.getLogger(ManagerTokenController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerTokenController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> loginUser(@RequestBody final LoginUserRequest request) {
      Function<TokenUserKeycloak, ResponseEntity<?>> onSuccess =
              token -> ResponseEntity.ok(ManagerUserPresenter.userLoginResponse.apply(token));

      return this.keycloakFacade
              .getLoginFlow()
              .loginUser(request.username(), request.password())
              .fold(
                      onError -> ResponseEntity.badRequest().body(onError),
                      onSuccess
              );
   }

   @Override
   public ResponseEntity<?> refreshToken(@RequestBody final RefreshTokenRequest request) {
      Function<TokenUserKeycloak, ResponseEntity<?>> onSuccess =
              token -> ResponseEntity.ok(ManagerUserPresenter.userLoginResponse.apply(token));
      return this.keycloakFacade
              .getLoginFlow()
              .refreshTokenUser(request.refreshToken())
              .fold(
                      onError -> ResponseEntity.badRequest().body(onError),
                      onSuccess
              );
   }

   @Override
   public ResponseEntity<?> logoutUser(@RequestBody final RefreshTokenRequest request) {
      return this.keycloakFacade
              .getLoginFlow()
              .logoutUser(request.refreshToken())
              .fold(
                      onError -> ResponseEntity.badRequest().body(onError),
                      onSuccess -> ResponseEntity.noContent().build()
              );
   }
}
