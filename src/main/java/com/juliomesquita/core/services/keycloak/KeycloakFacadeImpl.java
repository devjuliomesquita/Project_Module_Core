package com.juliomesquita.core.services.keycloak;

import com.juliomesquita.core.services.keycloak.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KeycloakFacadeImpl implements KeycloakFacade {
   private static final Logger log = LoggerFactory.getLogger(KeycloakFacadeImpl.class);

   private final LoginFlowKeycloakService loginFlowKeycloakService;
   private final UserFlowKeycloakService userFlowKeycloakService;
   private final RolesFlowKeycloakService rolesFlowKeycloakService;
   private final GroupsFlowKeycloakService groupsFlowKeycloakService;
   private final AssociateRolesService associateRolesService;

   public KeycloakFacadeImpl(
           final LoginFlowKeycloakService loginFlowKeycloakService,
           final UserFlowKeycloakService userFlowKeycloakService,
           final RolesFlowKeycloakService rolesFlowKeycloakService,
           final GroupsFlowKeycloakService groupsFlowKeycloakService,
           final AssociateRolesService associateRolesService
   ) {
      this.loginFlowKeycloakService = Objects.requireNonNull(loginFlowKeycloakService);
      this.userFlowKeycloakService = Objects.requireNonNull(userFlowKeycloakService);
      this.rolesFlowKeycloakService = Objects.requireNonNull(rolesFlowKeycloakService);
      this.groupsFlowKeycloakService = Objects.requireNonNull(groupsFlowKeycloakService);
      this.associateRolesService = Objects.requireNonNull(associateRolesService);
   }

   @Override
   public LoginFlowKeycloakService getLoginFlow() {
      return this.loginFlowKeycloakService;
   }

   @Override
   public UserFlowKeycloakService getUserFlow() {
      return this.userFlowKeycloakService;
   }

   @Override
   public RolesFlowKeycloakService getRoleFlow() {
      return this.rolesFlowKeycloakService;
   }

   @Override
   public GroupsFlowKeycloakService getGroupFlow() {
      return this.groupsFlowKeycloakService;
   }

   @Override
   public AssociateRolesService getAssociateFlow() {
      return this.associateRolesService;
   }
}
