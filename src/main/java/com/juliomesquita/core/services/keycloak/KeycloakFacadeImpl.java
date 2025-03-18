package com.juliomesquita.core.services.keycloak;

import com.juliomesquita.core.services.keycloak.interfaces.LoginFlowKeycloakService;
import com.juliomesquita.core.services.keycloak.interfaces.UserFlowKeycloakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KeycloakFacadeImpl implements KeycloakFacade {
   private static final Logger log = LoggerFactory.getLogger(KeycloakFacadeImpl.class);

   private final LoginFlowKeycloakService loginFlowKeycloakService;
   private final UserFlowKeycloakService userFlowKeycloakService;

   public KeycloakFacadeImpl(
           final LoginFlowKeycloakService loginFlowKeycloakService,
           final UserFlowKeycloakService userFlowKeycloakService
   ) {
      this.loginFlowKeycloakService = Objects.requireNonNull(loginFlowKeycloakService);
      this.userFlowKeycloakService = Objects.requireNonNull(userFlowKeycloakService);
   }

   @Override
   public LoginFlowKeycloakService getLoginFlow() {
      return this.loginFlowKeycloakService;
   }

   @Override
   public UserFlowKeycloakService getUserFlow() {
      return this.userFlowKeycloakService;
   }
}
