package com.juliomesquita.core.services.keycloak.implementations;

import com.juliomesquita.core.services.keycloak.dtos.userflow.UserDataKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.userflow.UserInformationKeycloak;
import com.juliomesquita.core.services.keycloak.interfaces.UserFlowKeycloakService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFlowKeycloakServiceImpl implements UserFlowKeycloakService {
   @Override
   public void updateUser(String userId, UserDataKeycloak data) {

   }

   @Override
   public void deleteUser(String userId) {

   }

   @Override
   public void activateOrDeactivateUser(String userId, Boolean enabled) {

   }

   @Override
   public List<UserInformationKeycloak> findUsers() {
      return List.of();
   }

   @Override
   public List<UserInformationKeycloak> findUsersByFilter(String email, Boolean enabled) {
      return List.of();
   }
}
