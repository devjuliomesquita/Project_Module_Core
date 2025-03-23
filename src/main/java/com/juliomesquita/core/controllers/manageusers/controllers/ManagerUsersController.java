package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.*;
import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.shared.pagination.Pagination;
import com.juliomesquita.core.shared.pagination.SearchQuery;
import com.juliomesquita.core.shared.validations.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/manager-user")
public class ManagerUsersController implements ManagerUserFlow {
   private static final Logger log = LoggerFactory.getLogger(ManagerUsersController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerUsersController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> createUser(@RequestBody final CreateUserRequest request) {
      Function<String, ResponseEntity<?>> onSuccess =
              id -> ResponseEntity.created(URI.create("/user/" + id)).body(id);

      final CreateUserKeycloak aCommand = ManagerUserPresenter.createUserKeycloak.apply(request);
      return this.keycloakFacade.getLoginFlow().createUser(aCommand)
              .fold(
                      onError -> ResponseEntity.unprocessableEntity().body(onError),
                      onSuccess
              );
   }

   @Override
   public ResponseEntity<?> resetPassword(final UUID userId, @RequestBody final ResetPasswordRequest request) {
      final CredentialsUserKeycloak credentialsUserKeycloak =
              ManagerUserPresenter.credentialsUserKeycloak.apply(request);
      return this.keycloakFacade
              .getLoginFlow()
              .resetPassword(userId.toString(), credentialsUserKeycloak)
              .fold(
                      onError -> ResponseEntity.badRequest().body(onError),
                      onSuccess -> ResponseEntity.ok().build()
              );
   }

   @Override
   public ResponseEntity<?> updateUser(final UUID userId, @RequestBody final UpdateUserRequest request) {
      final UserDataKeycloak userDataKeycloak = ManagerUserPresenter.userDataKeycloak.apply(request);
      return this.keycloakFacade
              .getUserFlow()
              .updateUser(userId.toString(), userDataKeycloak)
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.noContent().build()
              );
   }

   @Override
   public ResponseEntity<?> deleteUser(final UUID userId) {
      return this.keycloakFacade
              .getUserFlow()
              .deleteUser(userId.toString())
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.noContent().build()
              );
   }

   @Override
   public ResponseEntity<?> activateOrDeactivateUser(final UUID userId, @RequestBody final UserStatusRequest request) {
      return this.keycloakFacade
              .getUserFlow()
              .activateOrDeactivateUser(userId.toString(), request.enabled())
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.ok().build()
              );
   }

   @Override
   public ResponseEntity<?> findUsers(
           final Integer currentPage,
           final Integer itemsPerPage,
           final String terms,
           final String sort,
           final String direction
   ) {
      final SearchQuery searchQuery = new SearchQuery(currentPage, itemsPerPage, terms, sort, direction);

      final Function<Pagination<UserInformationKeycloak>, ResponseEntity<?>> onSuccess =
              pagination -> ResponseEntity.ok(
                      ManagerUserPresenter.paginationUserInfosResponse.apply(pagination));

      return this.keycloakFacade
              .getUserFlow()
              .findUsers(searchQuery)
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess
              );
   }
}
