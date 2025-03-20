package com.juliomesquita.core.controllers.manageusers.presenters;

import com.juliomesquita.core.controllers.manageusers.dtos.*;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CreateUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.CredentialsUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.loginflow.TokenUserKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;

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

   Function<List<UserInformationKeycloak>, ListUserInfosResponse> listUserInfosResponse = output ->{
      final List<UserInfosResponse> userInfosResponses = output.stream()
              .map(ManagerUserPresenter.userInfosResponse)
              .toList();
      return new ListUserInfosResponse(userInfosResponses);
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
}
