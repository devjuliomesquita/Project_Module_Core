package com.juliomesquita.core.services.keycloak;

import com.juliomesquita.core.services.keycloak.interfaces.*;

public interface KeycloakFacade {
   LoginFlowKeycloakService getLoginFlow();

   UserFlowKeycloakService getUserFlow();

   RolesFlowKeycloakService getRoleFlow();

   GroupsFlowKeycloakService getGroupFlow();

   AssociateRolesService getAssociateFlow();
}
