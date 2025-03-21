package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.shared.pagination.Pagination;
import com.juliomesquita.core.shared.pagination.SearchQuery;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

import java.util.List;

public interface UserFlowKeycloakService {
   Either<Notification, Void> updateUser(String userId, UserDataKeycloak data);

   Either<Notification, Void> deleteUser(String userId);

   Either<Notification, Void> activateOrDeactivateUser(String userId, Boolean enabled);

   Either<Notification, Pagination<UserInformationKeycloak>>  findUsers(SearchQuery searchQuery);
}
