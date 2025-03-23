package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerGroupFlow;
import com.juliomesquita.core.controllers.manageusers.dtos.CreateGroupRequest;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.groupflow.GroupDataKeycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/manager-user")
public class ManagerGroupsController implements ManagerGroupFlow {
   private static final Logger log = LoggerFactory.getLogger(ManagerGroupsController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerGroupsController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> createGroup(@RequestBody final CreateGroupRequest request) {
      Function<String, ResponseEntity<?>> onSuccess =
              id -> ResponseEntity.created(URI.create("/group/" + id)).body(id);
      return this.keycloakFacade
              .getGroupFlow()
              .createGroup(request.name())
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess
              );
   }

   @Override
   public ResponseEntity<?> updateGroup(final UUID groupId, @RequestBody final CreateGroupRequest request) {
      return this.keycloakFacade
              .getGroupFlow()
              .updateGroup(groupId.toString(), request.name())
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.ok().build()
              );
   }

   @Override
   public ResponseEntity<?> deleteGroup(final UUID groupId) {
      return this.keycloakFacade
              .getGroupFlow()
              .deleteGroup(groupId.toString())
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.noContent().build()
              );
   }

   @Override
   public ResponseEntity<?> findGroups() {
      Function<List<GroupDataKeycloak>, ResponseEntity<?>> onSuccess =
              groups -> ResponseEntity.ok(ManagerUserPresenter.listGroupInfoResponse.apply(groups));

      return this.keycloakFacade
              .getGroupFlow()
              .findGroups()
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess
              );
   }
}
