package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;
import com.juliomesquita.core.shared.validations.Notification;
import io.vavr.control.Either;

import java.util.List;

public interface RolesFlowKeycloakService {
   Either<Notification, String> createRole(CreateRoleKeycloak data);

   Either<Notification, Void> updateRole(String roleName, CreateRoleKeycloak data);

   Either<Notification, Void> deleteRole(String roleName);

   Either<Notification, List<RoleDataKeycloak>> findRoles();
}
