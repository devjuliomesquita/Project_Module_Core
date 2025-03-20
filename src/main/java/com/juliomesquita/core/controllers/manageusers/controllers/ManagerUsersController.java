package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerUsersAPI;
import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
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
      final CredentialsUserKeycloak credentialsUserKeycloak = ManagerUserPresenter.credentialsUserKeycloak.apply(
              request);
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
      final CreateRoleKeycloak createRoleKeycloak = ManagerUserPresenter.createRoleKeycloak.apply(request);
      this.keycloakFacade
              .getRoleFlow()
              .createRole(createRoleKeycloak);
      return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<?> updateRole(final String roleName, @RequestBody final CreateRoleRequest request) {
      final CreateRoleKeycloak updateRole = ManagerUserPresenter.createRoleKeycloak.apply(request);
      this.keycloakFacade
              .getRoleFlow()
              .updateRole(roleName, updateRole);
      return ResponseEntity.ok().build();
   }

   @Override
   public ResponseEntity<?> deleteRole(final String roleName) {
      this.keycloakFacade
              .getRoleFlow()
              .deleteRole(roleName);
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<ListRolesInfoResponse> findRoles() {
      final List<RoleDataKeycloak> roles = this.keycloakFacade
              .getRoleFlow()
              .findRoles();
      final ListRolesInfoResponse response = ManagerUserPresenter.listRoleInfoResponse.apply(roles);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<?> createGroup(@RequestBody final CreateGroupRequest request) {
      this.keycloakFacade
              .getGroupFlow()
              .createGroup(request.name());
      return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<?> updateGroup(final UUID groupId, @RequestBody final CreateGroupRequest request) {
      this.keycloakFacade
              .getGroupFlow()
              .updateGroup(groupId.toString(), request.name());
      return ResponseEntity.ok().build();
   }

   @Override
   public ResponseEntity<?> deleteGroup(final UUID groupId) {
      this.keycloakFacade
              .getGroupFlow()
              .deleteGroup(groupId.toString());
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<ListGroupsInfosResponse> findGroups() {
      final List<GroupDataKeycloak> groups = this.keycloakFacade
              .getGroupFlow()
              .findGroups();
      final ListGroupsInfosResponse response = ManagerUserPresenter.listGroupInfoResponse.apply(groups);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleUser(final UUID userId, @RequestBody final ListRolesRequest request) {
      final List<AssociateRolesKeycloak> roles = ManagerUserPresenter.listAssociateRolesKeycloak.apply(
              request.listRoles());
      if (request.associate()) {
         this.keycloakFacade
                 .getAssociateFlow()
                 .associateRoleUser(userId.toString(), roles);
      } else {
         this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateRoleUser(userId.toString(), roles);
      }
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleGroup(final UUID groupId, @RequestBody final ListRolesRequest request) {
      final List<AssociateRolesKeycloak> roles = ManagerUserPresenter.listAssociateRolesKeycloak.apply(
              request.listRoles());
      if (request.associate()) {
         this.keycloakFacade
                 .getAssociateFlow()
                 .associateRoleGroup(groupId.toString(), roles);
      } else {
         this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateRoleGroup(groupId.toString(), roles);
      }
      return ResponseEntity.noContent().build();
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateUserGroup(final UUID groupId, final UUID userId, final Boolean associate) {
      if (associate) {
         this.keycloakFacade
                 .getAssociateFlow()
                 .associateUserGroup(userId.toString(), groupId.toString());
      } else {
         this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateUserGroup(userId.toString(), groupId.toString());
      }
      return ResponseEntity.noContent().build();
   }
}
