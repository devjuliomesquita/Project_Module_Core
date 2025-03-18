package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.userflow.ListUserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;

import java.util.List;

public interface UserFlowKeycloakService {
   void updateUser(String userId, UserDataKeycloak data);

   void deleteUser(String userId);

   void activateOrDeactivateUser(String userId, Boolean enabled);

   List<UserInformationKeycloak> findUsers();

   List<UserInformationKeycloak> findUsersByFilter(String email, Boolean enabled);
}
