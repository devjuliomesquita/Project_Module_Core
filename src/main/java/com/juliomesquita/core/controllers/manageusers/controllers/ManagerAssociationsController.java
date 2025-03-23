package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerAssociationFlow;
import com.juliomesquita.core.controllers.manageusers.dtos.ListRolesRequest;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.associateflow.AssociateRolesKeycloak;
import com.juliomesquita.core.shared.validations.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/manager-user")
public class ManagerAssociationsController implements ManagerAssociationFlow {
   private static final Logger log = LoggerFactory.getLogger(ManagerAssociationsController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerAssociationsController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleUser(final UUID userId, @RequestBody final ListRolesRequest request) {
      Function<Notification, ResponseEntity<?>> onError =
              notification -> ResponseEntity.badRequest().body(notification);
      Function<Void, ResponseEntity<?>> onSuccess =
              response -> ResponseEntity.noContent().build();

      final List<AssociateRolesKeycloak> roles = ManagerUserPresenter.listAssociateRolesKeycloak.apply(
              request.listRoles());

      if (request.associate()) {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .associateRoleUser(userId.toString(), roles)
                 .fold(onError, onSuccess);
      } else {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateRoleUser(userId.toString(), roles)
                 .fold(onError, onSuccess);
      }
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateRoleGroup(final UUID groupId, @RequestBody final ListRolesRequest request) {
      Function<Notification, ResponseEntity<?>> onError =
              notification -> ResponseEntity.badRequest().body(notification);
      Function<Void, ResponseEntity<?>> onSuccess =
              response -> ResponseEntity.noContent().build();

      final List<AssociateRolesKeycloak> roles = ManagerUserPresenter.listAssociateRolesKeycloak.apply(
              request.listRoles());
      if (request.associate()) {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .associateRoleGroup(groupId.toString(), roles)
                 .fold(onError, onSuccess);
      } else {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateRoleGroup(groupId.toString(), roles)
                 .fold(onError, onSuccess);
      }
   }

   @Override
   public ResponseEntity<?> associateOrDisassociateUserGroup(final UUID groupId, final UUID userId, final Boolean associate) {
      Function<Notification, ResponseEntity<?>> onError =
              notification -> ResponseEntity.badRequest().body(notification);
      Function<Void, ResponseEntity<?>> onSuccess =
              response -> ResponseEntity.noContent().build();

      if (associate) {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .associateUserGroup(userId.toString(), groupId.toString())
                 .fold(onError, onSuccess);
      } else {
         return this.keycloakFacade
                 .getAssociateFlow()
                 .disassociateUserGroup(userId.toString(), groupId.toString())
                 .fold(onError, onSuccess);
      }
   }
}
