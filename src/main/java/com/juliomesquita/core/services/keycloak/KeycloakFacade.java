package com.juliomesquita.core.services.keycloak;

import com.juliomesquita.core.services.keycloak.interfaces.LoginFlowKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.UserFlowKeycloakService;

public interface KeycloakFacade {
   LoginFlowKeycloakService getLoginFlow();

   UserFlowKeycloakService getUserFlow();
}
