package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerUsersAPI;
import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class ManagerUsersController implements ManagerUsersAPI {
   private static final Logger log = LoggerFactory.getLogger(ManagerUsersController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerUsersController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> createUser(@RequestBody final CreateUserRequest request) {
      final CreateUserKeycloak aCommand = ManagerUserPresenter.createUserKeycloak.apply(request);
      this.keycloakFacade.getLoginFlow().createUser(aCommand);
      return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<?> loginUser(@RequestBody final LoginUserRequest request) {
      final TokenUserKeycloak tokenUserKeycloak = this.keycloakFacade
              .getLoginFlow()
              .loginUser(request.username(), request.password());
      final UserLoginResponse response = ManagerUserPresenter.userLoginResponse.apply(tokenUserKeycloak);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<?> refreshToken(@RequestBody final RefreshTokenRequest request) {
      final TokenUserKeycloak tokenUserKeycloak = this.keycloakFacade
              .getLoginFlow()
              .refreshTokenUser(request.refreshToken());
      final UserLoginResponse response = ManagerUserPresenter.userLoginResponse.apply(tokenUserKeycloak);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<?> logoutUser(@RequestBody final RefreshTokenRequest request) {
      this.keycloakFacade.getLoginFlow().logoutUser(request.refreshToken());
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<?> resetPassword(final UUID userId, @RequestBody final ResetPasswordRequest request) {
      final CredentialsUserKeycloak credentialsUserKeycloak = ManagerUserPresenter.credentialsUserKeycloak.apply(request);
      this.keycloakFacade
              .getLoginFlow()
              .resetPassword(userId.toString(), credentialsUserKeycloak);
      return ResponseEntity.ok().build();
   }

   @Override
   public ResponseEntity<?> updateUser(final UUID userId, @RequestBody final UpdateUserRequest request) {
      final UserDataKeycloak userDataKeycloak = ManagerUserPresenter.userDataKeycloak.apply(request);
      this.keycloakFacade
              .getUserFlow()
              .updateUser(userId.toString(), userDataKeycloak);
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<?> deleteUser(final UUID userId) {
      this.keycloakFacade
              .getUserFlow()
              .deleteUser(userId.toString());
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<?> activateOrDeactivateUser(final UUID userId, @RequestBody final UserStatusRequest request) {
      this.keycloakFacade
              .getUserFlow()
              .activateOrDeactivateUser(userId.toString(), request.enabled());
      return ResponseEntity.ok().build();
   }

   @Override
   public ResponseEntity<ListUserInfosResponse> findUsers() {
      final List<UserInformationKeycloak> users = this.keycloakFacade
              .getUserFlow()
              .findUsers();
      final ListUserInfosResponse response = ManagerUserPresenter.listUserInfosResponse.apply(users);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<ListUserInfosResponse> findUsersByFilter(final String email, final Boolean enabled) {
      final List<UserInformationKeycloak> users = this.keycloakFacade
              .getUserFlow()
              .findUsersByFilter(email, enabled);
      final ListUserInfosResponse response = ManagerUserPresenter.listUserInfosResponse.apply(users);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<?> createRole(@RequestBody final CreateRoleRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> updateRole(final String roleName, @RequestBody final CreateRoleRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> deleteRole(final String roleName) {
      return null;
   }

   @Override
   public ResponseEntity<ListRolesInfoResponse> findRoles() {
      return null;
   }

   @Override
   public ResponseEntity<?> createGroup(@RequestBody final CreateGroupRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> updateGroup(final UUID groupId, @RequestBody final CreateGroupRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> deleteGroup(final UUID groupId) {
      return null;
   }

   @Override
   public ResponseEntity<ListGroupsInfosResponse> findGroups() {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleUser(final UUID userId, @RequestBody final ListRolesRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleGroup(final UUID groupId, @RequestBody final ListRolesRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateUserGroup(final UUID groupId, final UUID userId) {
      return null;
   }
}
