package com.juliomesquita.core.controllers.manageusers.presenters;

import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.groupflow.AccessGroupDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.shared.pagination.Pagination;

import java.util.List;
import java.util.function.Function;

public interface ManagerUserPresenter {
   Function<CreateUserRequest, CreateUserKeycloak> createUserKeycloak = output ->
           new CreateUserKeycloak(
                   output.username(),
                   output.email(),
                   output.firstName(),
                   output.lastName(),
                   output.enabled(),
                   output.emailVerified(),
                   List.of(CredentialsUserKeycloak.create(output.password()))
           );

   Function<TokenUserKeycloak, UserLoginResponse> userLoginResponse = output ->
           new UserLoginResponse(
                   output.access_token(),
                   output.expires_in(),
                   output.refresh_expires_in(),
                   output.refresh_token(),
                   output.token_type(),
                   output.session_state(),
                   output.scope()
           );

   Function<ResetPasswordRequest, CredentialsUserKeycloak> credentialsUserKeycloak = output ->
           new CredentialsUserKeycloak(
                   "password",
                   output.password(),
                   output.temporary()
           );

   Function<UpdateUserRequest, UserDataKeycloak> userDataKeycloak = output ->
           new UserDataKeycloak(
                   output.firstName(),
                   output.lastName(),
                   output.email(),
                   true
           );

   Function<Pagination<UserInformationKeycloak>, Pagination<UserInfosResponse>> paginationUserInfosResponse = output -> {
      final List<UserInfosResponse> userInfosResponses = output.items().stream()
              .map(ManagerUserPresenter.userInfosResponse)
              .toList();
      return output.convert(userInfosResponses);
   };

   Function<UserInformationKeycloak, UserInfosResponse> userInfosResponse = output ->
           new UserInfosResponse(
                   output.id(),
                   output.username(),
                   output.firstName(),
                   output.lastName(),
                   output.email(),
                   output.emailVerified(),
                   output.createdTimestamp(),
                   output.enabled()
           );

   Function<CreateRoleRequest, CreateRoleKeycloak> createRoleKeycloak = output ->
           new CreateRoleKeycloak(
                   output.name(),
                   output.description()
           );

   Function<RoleDataKeycloak, RoleInfoResponse> roleInfoResponse = output ->
           new RoleInfoResponse(
                   output.id(),
                   output.name(),
                   output.description(),
                   output.composite(),
                   output.clientRole(),
                   output.containerId()
           );

   Function<List<RoleDataKeycloak>, ListRolesInfoResponse> listRoleInfoResponse = output -> {
      final List<RoleInfoResponse> listRoles = output.stream()
              .map(ManagerUserPresenter.roleInfoResponse)
              .toList();
      return new ListRolesInfoResponse(listRoles);
   };


   Function<AccessGroupDataKeycloak, AccessGroupDataResponse> accessGroupResponse = output ->
           new AccessGroupDataResponse(
                   output.view(),
                   output.viewMembers(),
                   output.manageMembers(),
                   output.manage(),
                   output.manageMembership()
           );

   Function<GroupDataKeycloak, GroupInfoResponse> groupInfoResponse = output ->
           new GroupInfoResponse(
                   output.id(),
                   output.name(),
                   output.path(),
                   ManagerUserPresenter.accessGroupResponse.apply(output.access())
           );

   Function<List<GroupDataKeycloak>, ListGroupsInfosResponse> listGroupInfoResponse = output -> {
      final List<GroupInfoResponse> groups = output.stream()
              .map(ManagerUserPresenter.groupInfoResponse)
              .toList();
      return new ListGroupsInfosResponse(groups);
   };

   Function<ListRolesRequest.RoleRequest, AssociateRolesKeycloak> associateRolesKeycloak = output ->
           new AssociateRolesKeycloak(
                   output.id(),
                   output.name()
           );

   Function<List<ListRolesRequest.RoleRequest>, List<AssociateRolesKeycloak>> listAssociateRolesKeycloak = output ->
           output.stream()
                   .map(ManagerUserPresenter.associateRolesKeycloak)
                   .toList();
}
