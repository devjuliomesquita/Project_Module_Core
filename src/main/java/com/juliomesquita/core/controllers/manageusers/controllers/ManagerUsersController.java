package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerUsersAPI;
import com.juliomesquita.core.controllers.manageusers.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ManagerUsersController implements ManagerUsersAPI {
   @Override
   public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> loginUser(LoginUserRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> logoutUser(RefreshTokenRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> resetPassword(UUID userId, ResetPasswordRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> updateUser(UUID userId, UpdateUserRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> deleteUser(UUID userId) {
      return null;
   }

   @Override
   public ResponseEntity<?> activateOrDeactivateUser(UUID userId, UserStatusRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<ListUserInfosResponse> findUsers() {
      return null;
   }

   @Override
   public ResponseEntity<ListUserInfosResponse> findUsersByFilter(String email, Boolean enabled) {
      return null;
   }

   @Override
   public ResponseEntity<?> createRole(CreateRoleRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> updateRole(String roleName, CreateRoleRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> deleteRole(String roleName) {
      return null;
   }

   @Override
   public ResponseEntity<ListRolesInfoResponse> findRoles() {
      return null;
   }

   @Override
   public ResponseEntity<?> createGroup(CreateGroupRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> updateGroup(UUID groupId, CreateGroupRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> deleteGroup(UUID groupId) {
      return null;
   }

   @Override
   public ResponseEntity<ListGroupsInfosResponse> findGroups() {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleUser(UUID userId, ListRolesRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleGroup(UUID groupId, ListRolesRequest request) {
      return null;
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateUserGroup(UUID groupId, UUID userId) {
      return null;
   }
}
