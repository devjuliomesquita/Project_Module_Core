package com.juliomesquita.core.services.keycloak.interfaces;

import com.juliomesquita.core.services.keycloak.dtos.roleflow.CreateRoleKeycloak;
import com.juliomesquita.core.services.keycloak.dtos.roleflow.RoleDataKeycloak;

import java.util.List;

public interface RolesFlowKeycloakService {
   void createRole(CreateRoleKeycloak data);

   void updateRole(String roleName, CreateRoleKeycloak data);

   void deleteRole(String roleName);

   List<RoleDataKeycloak> findRoles();
}
