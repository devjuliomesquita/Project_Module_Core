package com.juliomesquita.core.controllers.manageusers.controllers;

import com.juliomesquita.core.controllers.manageusers.api.ManagerRoleFlow;
import com.juliomesquita.core.controllers.manageusers.dtos.CreateRoleRequest;
import com.juliomesquita.core.controllers.manageusers.presenters.ManagerUserPresenter;
import com.juliomesquita.core.services.keycloak.KeycloakFacade;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/manager-user")
public class ManagerRolesController implements ManagerRoleFlow {
   private static final Logger log = LoggerFactory.getLogger(ManagerRolesController.class);

   private final KeycloakFacade keycloakFacade;

   public ManagerRolesController(final KeycloakFacade keycloakFacade) {
      this.keycloakFacade = Objects.requireNonNull(keycloakFacade);
   }

   @Override
   public ResponseEntity<?> createRole(@RequestBody final CreateRoleRequest request) {
      Function<String, ResponseEntity<?>> onSuccess =
              id -> ResponseEntity.created(URI.create("/role/" + id)).body(id);

      final CreateRoleKeycloak createRoleKeycloak = ManagerUserPresenter.createRoleKeycloak.apply(request);

      return this.keycloakFacade
              .getRoleFlow()
              .createRole(createRoleKeycloak)
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess
              );
   }

   @Override
   public ResponseEntity<?> updateRole(final String roleName, @RequestBody final CreateRoleRequest request) {
      final CreateRoleKeycloak updateRole = ManagerUserPresenter.createRoleKeycloak.apply(request);
      return this.keycloakFacade
              .getRoleFlow()
              .updateRole(roleName, updateRole)
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.ok().build()
              );
   }

   @Override
   public ResponseEntity<?> deleteRole(final String roleName) {
      return this.keycloakFacade
              .getRoleFlow()
              .deleteRole(roleName)
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess -> ResponseEntity.noContent().build()
              );
   }

   @Override
   public ResponseEntity<?> findRoles() {
      Function<List<RoleDataKeycloak>, ResponseEntity<?>> onSuccess =
              roles -> ResponseEntity.ok(ManagerUserPresenter.listRoleInfoResponse.apply(roles));
      return this.keycloakFacade
              .getRoleFlow()
              .findRoles()
              .fold(
                      ResponseEntity.badRequest()::body,
                      onSuccess
              );
   }
}
